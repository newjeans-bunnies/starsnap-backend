package com.photo.server.starsnap.adapter_web

import com.photo.server.starsnap.adapter_infrastructure.global.security.jwt.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableConfigurationProperties(JwtProperties::class)
@EnableJpaRepositories("com.photo.server.starsnap.adapter_infrastructure")
@EntityScan(basePackages = ["com.photo.server.starsnap.adapter_infrastructure"])
@SpringBootApplication(
    scanBasePackages = ["com.photo.server.starsnap"]
)
@EnableFeignClients(basePackages = ["com.photo.server.starsnap"])
@Import(FeignClientsConfiguration::class)
@EnableRedisRepositories("com.photo.server.starsnap.adapter_infrastructure")
@EnableJpaAuditing
class StarSnapApplication

fun main(args: Array<String>) {
    runApplication<StarSnapApplication>(*args)
}