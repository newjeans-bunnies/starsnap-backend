package com.photo.server.starsnap.adapter_web.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.photo.server.starsnap.exception.global.error.custom.CustomErrorProperty
import com.photo.server.starsnap.exception.global.error.custom.CustomException
import com.photo.server.starsnap.exception.global.error.exception.InternalServerErrorException
import com.photo.server.starsnap.exception.global.error.response.ErrorResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter

class GlobalExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            when (e) {
                is CustomException -> errorToJson(e.errorProperty, response)
                else -> {
                    errorToJson(InternalServerErrorException.errorProperty, response)
                    e.printStackTrace()
                }
            }
        }
    }

    private fun errorToJson(errorProperty: CustomErrorProperty, response: HttpServletResponse) {
        response.status = errorProperty.status()
        response.characterEncoding = "UTF-8"
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(objectMapper.writeValueAsString(
            ErrorResponse(
                status = errorProperty.status(),
                message = errorProperty.message()
            )
        ))
    }
}