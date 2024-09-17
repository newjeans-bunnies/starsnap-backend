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
        //auth
        registry.addMapping("/api/auth/**").allowedOrigins(allowedOrigins)
            .allowedMethods("POST", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("Authorization").allowCredentials(true)

        //report
        registry.addMapping("/api/report/**").allowedOrigins(allowedOrigins)
            .allowedMethods("POST", "GET", "OPTIONS")
            .allowedHeaders("Authorization").allowCredentials(true)

        //snap
        registry.addMapping("/api/snap/**").allowedOrigins(allowedOrigins)
            .allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("Authorization").allowCredentials(true)

        //user
        registry.addMapping("/api/user/**").allowedOrigins(allowedOrigins)
            .allowedMethods("GET", "POST", "PATCH", "OPTIONS")
            .allowedHeaders("Authorization").allowCredentials(true)
    }
}