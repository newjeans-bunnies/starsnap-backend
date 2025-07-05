package com.photo.server.starsnap.adapter_infrastructure.global.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class CustomAccessDeniedHandler(
    private val objectMapper: ObjectMapper
) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_FORBIDDEN
        val body = mapOf<String, Any?>(
            "timestamp" to OffsetDateTime.now(),
            "status" to HttpServletResponse.SC_FORBIDDEN,
            "error" to HttpStatus.FORBIDDEN.reasonPhrase,
            "message" to accessDeniedException.message,
            "path" to request.servletPath
        )
        objectMapper.writeValue(response.outputStream, body)
    }
}