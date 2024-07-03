package com.photo.server.starsnap

import com.photo.server.starsnap.global.security.jwt.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties::class)
class StarsnapApplication

fun main(args: Array<String>) {
    runApplication<StarsnapApplication>(*args)
}
