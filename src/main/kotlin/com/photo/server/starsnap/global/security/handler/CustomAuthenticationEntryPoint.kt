package com.photo.server.starsnap.global.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_NOT_FOUND
        val body = mapOf<String, Any?>(
            "timestamp" to OffsetDateTime.now(),
            "status" to HttpServletResponse.SC_NOT_FOUND,
            "error" to HttpStatus.FORBIDDEN.reasonPhrase,
            "message" to authException.message,
            "path" to request.servletPath
        )
        objectMapper.writeValue(response.outputStream, body)
    }
}