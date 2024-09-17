package com.photo.server.starsnap.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.photo.server.starsnap.domain.auth.service.AuthService
import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.report.service.ReportService
import com.photo.server.starsnap.global.security.jwt.JwtParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtParser: JwtParser,
    private val objectMapper: ObjectMapper,
    private val accessDeniedHandler: AccessDeniedHandler,
    private val authenticationEntryPoint: AuthenticationEntryPoint
) {


    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        authService: AuthService,
        reportService: ReportService
    ): SecurityFilterChain {
        http.csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { authorize ->
                // auth
                authorize.requestMatchers("api/auth/email/**").permitAll()
                authorize.requestMatchers("api/auth/**").permitAll()
                authorize.requestMatchers(HttpMethod.DELETE, "api/auth").hasAnyAuthority(Authority.ADMIN.name, Authority.USER.name)
                // report
                authorize.requestMatchers(HttpMethod.GET, "api/report/**").hasAnyAuthority(Authority.ADMIN.name)
                authorize.requestMatchers(HttpMethod.POST, "api/report/**").hasAnyAuthority(Authority.ADMIN.name, Authority.USER.name)
                // snap
                authorize.requestMatchers("api/snap/**").hasAnyAuthority(Authority.ADMIN.name, Authority.USER.name)
                // user
                authorize.requestMatchers("api/user/**").hasAnyAuthority(Authority.ADMIN.name, Authority.USER.name)

                //other
                authorize.requestMatchers(HttpMethod.OPTIONS, "api/**").permitAll()

            }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling
                    .accessDeniedHandler(accessDeniedHandler)
                    .authenticationEntryPoint(authenticationEntryPoint)
            }

            .apply(FilterConfig(jwtParser, objectMapper))

        return http.build()
    }

    @Bean
    protected fun passwordEncoder() = BCryptPasswordEncoder()
}