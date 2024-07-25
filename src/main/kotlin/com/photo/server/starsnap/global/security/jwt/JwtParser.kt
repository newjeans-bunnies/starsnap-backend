package com.photo.server.starsnap.global.security.jwt

import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.auth.type.Token
import com.photo.server.starsnap.global.error.exception.*
import com.photo.server.starsnap.global.security.principle.CustomUserDetailsService
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class JwtParser(
    private val jwtProperties: JwtProperties,
    private val userDetailsService: CustomUserDetailsService,
) {
    fun getAuthentication(token: String): Authentication {
        val claims = getClaims(token, Token.ACCESS)

        if (claims.header[Header.JWT_TYPE] != JwtProvider.ACCESS) throw InvalidTokenException
        val userDetails = getDetails(claims.body)

        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getClaims(token: String, tokenType: Token): Jws<Claims> {
        return try {
            val key = when (tokenType) {
                Token.ACCESS -> Keys.hmacShaKeyFor(jwtProperties.accessSecretKey.toByteArray(StandardCharsets.UTF_8))
                Token.REFRESH -> Keys.hmacShaKeyFor(jwtProperties.refreshSecretKey.toByteArray(StandardCharsets.UTF_8))
            }
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
        } catch (e: Exception) {
            when (e) {
                is InvalidClaimException -> throw InvalidTokenException
                is ExpiredJwtException -> throw ExpiredTokenException
                is JwtException -> throw UnexpectedTokenException
                else -> throw InternalServerErrorException
            }
        }
    }

    private fun getDetails(body: Claims): UserDetails {
        return when (body["authority"].toString()) {
            Authority.USER.name, Authority.ADMIN.name, Authority.STAR.name -> userDetailsService.loadUserByUsername(body.id)
            else -> throw InvalidRoleException
        }
    }
}