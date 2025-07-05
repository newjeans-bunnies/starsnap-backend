package com.photo.server.starsnap.adapter_usecase.auth.usecase

import com.photo.server.starsnap.adapter_infrastructure.auth.repository.RefreshTokenRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.global.security.jwt.JwtParser
import com.photo.server.starsnap.adapter_infrastructure.global.security.jwt.JwtProperties
import com.photo.server.starsnap.adapter_infrastructure.global.security.jwt.JwtProvider
import com.photo.server.starsnap.domain.auth.entity.RefreshToken
import com.photo.server.starsnap.domain.auth.type.Token
import com.photo.server.starsnap.domain.user.type.Authority
import com.photo.server.starsnap.exception.global.error.exception.InternalServerErrorException
import com.photo.server.starsnap.exception.global.error.exception.InvalidTokenException
import com.photo.server.starsnap.exception.global.error.exception.UnexpectedTokenException
import com.photo.server.starsnap.usecase.auth.dto.TokenDto
import com.photo.server.starsnap.usecase.auth.usecase.ReissueTokenUseCase
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.Key

@Service
class ReissueTokenUseCaseImpl(
    private val jwtProperties: JwtProperties,
    private val jwtProvider: JwtProvider,
    private val jwtParser: JwtParser,
    private val refreshTokenRepositoryImpl: RefreshTokenRepositoryImpl
): ReissueTokenUseCase {

    // 토큰 재발급
    override fun reissueToken(refreshToken: String, accessToken: String): TokenDto {

        // refreshToken Claims
        val refreshTokenClaims = jwtParser.getClaims(refreshToken, Token.REFRESH)

        checkValidAccessToken(accessToken, refreshTokenClaims)

        val data = checkValidRefreshToken(refreshToken)
        val tokenAuthority = Authority.valueOf(refreshTokenClaims.header["authority"].toString())

        val tokenDto = jwtProvider.receiveToken(data.id, tokenAuthority)

        val refreshToken = RefreshToken(
            token = tokenDto.refreshToken, id = data.id
        )

        refreshTokenRepositoryImpl.save(refreshToken)

        return tokenDto
    }

    // accessToken 유효성 검사
    override fun checkValidAccessToken(accessToken: String, refreshTokenClaims: Jws<Claims>) {
        if (refreshTokenClaims.header[Header.JWT_TYPE] != JwtProvider.REFRESH || getClaims(accessToken)) throw InvalidTokenException
    }

    // refreshToken 유효성 검사
    override fun checkValidRefreshToken(refreshToken: String) =
        refreshTokenRepositoryImpl.findByToken(refreshToken) ?: throw UnexpectedTokenException

    // accessToken Claims 검사
    override fun getClaims(token: String): Boolean {
        return try {
            val key: Key = Keys.hmacShaKeyFor(jwtProperties.accessSecretKey.toByteArray(StandardCharsets.UTF_8))
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            false
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> false // jwt token 만료
                else -> throw InternalServerErrorException
            }
        }
    }
}