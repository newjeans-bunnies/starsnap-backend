package com.photo.server.starsnap.adapter_web.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.photo.server.starsnap.adapter_infrastructure.global.security.jwt.JwtParser
import com.photo.server.starsnap.adapter_web.filter.FilterConfig
import com.photo.server.starsnap.adapter_web.filter.JwtFilter
import com.photo.server.starsnap.adapter_web.filter.LoggingFilter
import com.photo.server.starsnap.adapter_web.filter.TokenExceptionFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtParser: JwtParser,
    private val objectMapper: ObjectMapper,
    private val accessDeniedHandler: AccessDeniedHandler,
    private val authenticationEntryPoint: AuthenticationEntryPoint,
    private val jwtFilter: JwtFilter,
    private val loggingFilter: LoggingFilter
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .requestMatchers("/error", "/favicon.ico", "/.well-known/assetlinks.json")
        }
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity
    ): SecurityFilterChain {
        http.csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { authorize ->
                // oauth
                authorize.requestMatchers(
                    AntPathRequestMatcher("/auth/success"),
                    AntPathRequestMatcher("/api"),
                    AntPathRequestMatcher("/logout")
                ).permitAll()

                authorize.requestMatchers(HttpMethod.PATCH, "/api/oauth/**")
                    .hasAnyAuthority("ADMIN", "USER")
                authorize.requestMatchers(HttpMethod.POST, "/api/oauth/**").permitAll()

                // auth
                authorize.requestMatchers( HttpMethod.POST,"api/auth/signup").permitAll()
                authorize.requestMatchers( HttpMethod.POST,"api/auth/login").permitAll()
                authorize.requestMatchers( HttpMethod.POST,"api/auth/email/send").permitAll()
                authorize.requestMatchers( HttpMethod.POST,"api/auth/email/verify").permitAll()
                authorize.requestMatchers(HttpMethod.DELETE, "api/auth/secession")
                    .hasAnyAuthority("ADMIN", "USER")
                authorize.requestMatchers(HttpMethod.PATCH, "/api/auth/refresh").permitAll()


                // report
                authorize.requestMatchers(HttpMethod.GET, "api/report/**").hasAnyAuthority("ADMIN")
                authorize.requestMatchers(HttpMethod.POST, "api/report/**")
                    .hasAnyAuthority("ADMIN", "USER")

                // snap
                authorize.requestMatchers("api/snap/**").permitAll()
//                authorize.requestMatchers("api/snap/**").hasAnyAuthority(Authority.ADMIN.name, Authority.USER.name)


                // user
                authorize.requestMatchers(HttpMethod.PATCH, "api/user/change-data")
                    .hasAnyAuthority("ADMIN", "USER")
                authorize.requestMatchers(HttpMethod.GET, "api/user").permitAll()

                //other
                authorize.requestMatchers(HttpMethod.OPTIONS, "api/**").permitAll()

                // valid
                authorize.requestMatchers(HttpMethod.GET, "/api/auth/valid/username").permitAll()
                authorize.requestMatchers(HttpMethod.GET, "/api/auth/valid/email").permitAll()


            }

            .addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(jwtFilter, loggingFilter::class.java)
            .addFilterBefore(TokenExceptionFilter(), jwtFilter::class.java)


            .exceptionHandling { exceptionHandling ->
                exceptionHandling
                    .accessDeniedHandler(accessDeniedHandler)
                    .authenticationEntryPoint(authenticationEntryPoint)
            }

            .apply(FilterConfig(jwtParser, objectMapper))

        return http.build()
    }
}