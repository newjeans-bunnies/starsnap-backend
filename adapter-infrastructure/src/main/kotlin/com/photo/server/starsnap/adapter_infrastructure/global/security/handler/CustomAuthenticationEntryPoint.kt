package com.photo.server.starsnap.adapter_infrastructure.global.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
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
    private val logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint::class.java)

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val method = request.method
        val uri = request.requestURI
        val queryString = request.queryString?.let { "?$it" } ?: ""
        val remoteAddr = request.remoteAddr
        logger.info("Incoming Request: $method $uri$queryString from $remoteAddr")
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