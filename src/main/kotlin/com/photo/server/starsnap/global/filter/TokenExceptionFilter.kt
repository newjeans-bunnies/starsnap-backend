package com.photo.server.starsnap.global.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class TokenExceptionFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            println("TokenExceptionFilter")
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            response.sendError(403, e.message)
        }
    }
}