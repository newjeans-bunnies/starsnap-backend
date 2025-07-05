package com.photo.server.starsnap.adapter_web.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class LoggingFilter : OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(LoggingFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 요청 정보 로깅
        val method = request.method
        val uri = request.requestURI
        val queryString = request.queryString?.let { "?$it" } ?: ""
        val remoteAddr = request.remoteAddr

        logger.info("Incoming Request: $method $uri$queryString from $remoteAddr")

        // 추가로 헤더나 Body 등 로그를 찍고 싶다면 여기서 추가 가능 (주의: Body는 InputStream 한 번 소비하면 재사용이 어려울 수 있음)

        // 다음 필터로 요청 넘기기
        filterChain.doFilter(request, response)
    }
}