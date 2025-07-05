package com.photo.server.starsnap.adapter_infrastructure.global.security.handler

import com.photo.server.starsnap.adapter_infrastructure.global.security.jwt.JwtProvider
import com.photo.server.starsnap.domain.user.type.Authority
import com.photo.server.starsnap.usecase.auth.dto.TokenDto
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.net.URLEncoder

@Component
class OAuth2SuccessHandler(
    private val jwtProvider: JwtProvider
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val userId = authentication.name

        val authorities = authentication.authorities
            .map { it.authority }
            .mapNotNull { Authority.values().find { authority -> authority.name == it } }

        val primaryAuthority = authorities.firstOrNull() ?: Authority.USER

        val tokenDto: TokenDto = jwtProvider.receiveToken(userId, primaryAuthority)

        addCookie(response, "accessToken", tokenDto.accessToken)
        addCookie(response, "refreshToken", tokenDto.refreshToken)

        response.sendRedirect("http://127.0.0.1:3000")
    }


    private fun addCookie(response: HttpServletResponse, name: String, value: String) {
        val cookie = Cookie(name, URLEncoder.encode(value, "UTF-8"))
        cookie.isHttpOnly = true
        cookie.path = "/"
        response.addCookie(cookie)
    }
}