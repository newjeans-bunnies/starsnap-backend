package com.photo.server.starsnap.domain.auth.service

import com.photo.server.starsnap.domain.auth.RefreshTokenEntity
import com.photo.server.starsnap.domain.auth.controller.dto.TokenDto
import com.photo.server.starsnap.domain.auth.repository.RefreshTokenRepository
import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.auth.type.Token
import com.photo.server.starsnap.global.error.exception.InternalServerErrorException
import com.photo.server.starsnap.global.error.exception.InvalidTokenException
import com.photo.server.starsnap.global.error.exception.UnexpectedTokenException
import com.photo.server.starsnap.global.security.jwt.JwtParser
import com.photo.server.starsnap.global.security.jwt.JwtProperties
import com.photo.server.starsnap.global.security.jwt.JwtProvider
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.Key

@Service
class ReissueTokenService(
    private val jwtProperties: JwtProperties,
    private val jwtProvider: JwtProvider,
    private val jwtParser: JwtParser,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    val log: Logger = LoggerFactory.getLogger(this.javaClass.getSimpleName())

    fun reissueToken(refreshToken: String, accessToken: String): TokenDto {

        // refreshToken Claims
        val refreshTokenClaims = jwtParser.getClaims(refreshToken, Token.REFRESH)

        checkValidAccessToken(accessToken, refreshTokenClaims)

        val data = checkValidRefreshToken(refreshToken)
        val tokenAuthority = Authority.valueOf(refreshTokenClaims.header["authority"].toString())

        val tokenDto = jwtProvider.receiveToken(data.id, tokenAuthority)

        val refreshTokenEntity = RefreshTokenEntity(
            token = tokenDto.refreshToken, id = data.id
        )

        refreshTokenRepository.save(refreshTokenEntity)

        return tokenDto
    }

    private fun checkValidAccessToken(accessToken: String, refreshTokenClaims: Jws<Claims>) {
        if (refreshTokenClaims.header[Header.JWT_TYPE] != JwtProvider.REFRESH || getClaims(accessToken)) throw InvalidTokenException
    }

    private fun checkValidRefreshToken(refreshToken: String) =
        refreshTokenRepository.findByToken(refreshToken) ?: throw UnexpectedTokenException

    private fun getClaims(token: String): Boolean {
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