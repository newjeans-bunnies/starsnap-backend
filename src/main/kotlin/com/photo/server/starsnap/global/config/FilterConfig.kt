package com.photo.server.starsnap.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.photo.server.starsnap.global.filter.GlobalExceptionFilter
import com.photo.server.starsnap.global.filter.JwtFilter
import com.photo.server.starsnap.global.filter.StarFilter
import com.photo.server.starsnap.global.security.jwt.JwtParser
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
class FilterConfig(
    private val jwtParser: JwtParser,
    private val objectMapper: ObjectMapper
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        builder.addFilterBefore(JwtFilter(jwtParser), UsernamePasswordAuthenticationFilter::class.java)
        builder.addFilterBefore(GlobalExceptionFilter(objectMapper), JwtFilter::class.java)
        builder.addFilterBefore(StarFilter(jwtParser), UsernamePasswordAuthenticationFilter::class.java)
    }
}