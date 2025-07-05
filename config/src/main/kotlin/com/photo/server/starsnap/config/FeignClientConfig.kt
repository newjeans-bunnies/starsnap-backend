package com.photo.server.starsnap.config

import feign.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignClientConfig {
    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.BASIC
    }
}
