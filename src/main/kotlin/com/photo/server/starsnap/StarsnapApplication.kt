package com.photo.server.starsnap

import com.photo.server.starsnap.global.security.jwt.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@SpringBootApplication
@EnableRedisRepositories
@EnableFeignClients
@EnableJpaAuditing
@EnableConfigurationProperties(JwtProperties::class)
class StarsnapApplication

fun main(args: Array<String>) {
    runApplication<StarsnapApplication>(*args)
}
