package com.photo.server.starsnap.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    @Value("\${cors.originPatterns}") private val corsOriginPatterns: String
) : WebMvcConfigurer {
    val allowedOrigins = corsOriginPatterns

    override fun addCorsMappings(registry: CorsRegistry) {
        super.addCorsMappings(registry)

    }
}