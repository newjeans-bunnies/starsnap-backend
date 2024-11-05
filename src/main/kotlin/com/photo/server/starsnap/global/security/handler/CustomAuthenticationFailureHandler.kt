package com.photo.server.starsnap.global.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationFailureHandler : AuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        if (exception.message.equals("REDIRECT_TO_SIGNUP")) {
            println("회원가입 해야됨")
            response.sendRedirect("http://127.0.0.1:3000/oauth/signup")
        } else {
            response.sendRedirect("http://127.0.0.1:3000/auth/failure?error")
        }
    }
}