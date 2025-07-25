package com.photo.server.starsnap.adapter_infrastructure.global.security.jwt

import com.photo.server.starsnap.domain.user.type.Authority
import com.photo.server.starsnap.usecase.auth.dto.TokenDto
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.time.LocalDateTime
import java.util.Date

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties
) {
    companion object {
        const val ACCESS = "access"
        const val REFRESH = "refresh"
        const val REGISTER = "register"
        const val AUTHORITY = "authority"
    }

    private val accessKey: Key = Keys.hmacShaKeyFor(jwtProperties.accessSecretKey.toByteArray(StandardCharsets.UTF_8))
    private val refreshKey: Key = Keys.hmacShaKeyFor(jwtProperties.refreshSecretKey.toByteArray(StandardCharsets.UTF_8))
    private val registerKey: Key =
        Keys.hmacShaKeyFor(jwtProperties.registerSecretKey.toByteArray(StandardCharsets.UTF_8))

    fun receiveToken(id: String, authority: Authority) = TokenDto(
        accessToken = generateJwtAccessToken(id, authority),
        expiredAt = LocalDateTime.now().plusSeconds(jwtProperties.accessExp),
        refreshToken = generateJwtRefreshToken(authority),
        authority = authority.name
    )

    fun registerToken(email: String, sub: String): String = generateJwtRegisterToken(email, sub)

    private fun generateJwtRegisterToken(email: String, sub: String) =
        Jwts.builder().signWith(registerKey, SignatureAlgorithm.HS256).setHeaderParam(Header.JWT_TYPE, REGISTER).setId(sub)
            .claim("email", email).setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.registerExp * 1000)).compact()

    private fun generateJwtAccessToken(uuid: String, authority: Authority) =
        Jwts.builder().signWith(accessKey, SignatureAlgorithm.HS256).setHeaderParam(Header.JWT_TYPE, ACCESS).setId(uuid)
            .claim(AUTHORITY, authority).setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.accessExp * 1000)).compact()

    private fun generateJwtRefreshToken(authority: Authority) =
        Jwts.builder().signWith(refreshKey, SignatureAlgorithm.HS256).setHeaderParam(Header.JWT_TYPE, REFRESH)
            .setHeaderParam(AUTHORITY, authority.name).setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.refreshExp * 1000)).compact()
}