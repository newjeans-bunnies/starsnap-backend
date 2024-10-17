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
            // 회원가입 추가 정보 입력 페이지로 리디렉션
            response.sendRedirect("api/oauth/signup")
        } else {
            response.sendRedirect("api/oauth/login?error")
        }
    }
}