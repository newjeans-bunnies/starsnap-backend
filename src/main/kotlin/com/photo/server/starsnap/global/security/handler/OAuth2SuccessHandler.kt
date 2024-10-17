package com.photo.server.starsnap.global.security.handler

import com.photo.server.starsnap.domain.auth.dto.TokenDto
import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.global.security.jwt.JwtProvider
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class OAuth2SuccessHandler(
    private val jwtProvider: JwtProvider
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication
    ) {
        println("OAuth2SuccessHandler")

        val userId = authentication.name

        val authorities = authentication.authorities
            .map { it.authority }
            .mapNotNull { Authority.values().find { authority -> authority.name == it } }

        val primaryAuthority = authorities.firstOrNull() ?: Authority.USER

        val tokenDto: TokenDto = jwtProvider.receiveToken(userId, primaryAuthority)
        println("token: $tokenDto")

        // 토큰 전달을 위한 redirect
        val redirectUrl = UriComponentsBuilder.fromUriString("/auth/success")
            .queryParam("accessToken", tokenDto.accessToken)
            .queryParam("refreshToken", tokenDto.refreshToken)
            .build().toUriString()

        response?.sendRedirect(redirectUrl)
    }
}