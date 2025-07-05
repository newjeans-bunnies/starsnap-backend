package com.photo.server.starsnap.adapter_infrastructure.global.security.handler

import com.photo.server.starsnap.adapter_infrastructure.global.security.jwt.JwtProvider
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationFailureHandler(
    private val jwtProvider: JwtProvider
) : AuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        if (exception is OAuth2AuthenticationException) {
            val description = exception.error.description
            val email = description.substringAfter("email=").substringBefore(",")
            val sub = description.substringAfter("sub=").substringBefore(",")
            val picture = description.substringAfter("picture=")
            val registerToken = jwtProvider.registerToken(email, sub)

            // 추출한 값들을 개별 URL 파라미터로 전달
            val redirectUrl = "http://127.0.0.1:3000/oauth/signup?email=${email}&sub=${sub}&picture=${picture}&registerToken=${registerToken}"
            response.sendRedirect(redirectUrl)
        } else {
            response.sendRedirect("http://127.0.0.1:3000/auth/failure?error")
        }
    }
}