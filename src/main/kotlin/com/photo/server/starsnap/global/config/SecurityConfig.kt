package com.photo.server.starsnap.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.photo.server.starsnap.domain.auth.service.AuthService
import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.report.service.ReportService
import com.photo.server.starsnap.global.filter.JwtFilter
import com.photo.server.starsnap.global.filter.LoggingFilter
import com.photo.server.starsnap.global.filter.TokenExceptionFilter
import com.photo.server.starsnap.global.security.handler.CustomAuthenticationFailureHandler
import com.photo.server.starsnap.global.security.handler.OAuth2SuccessHandler
import com.photo.server.starsnap.global.security.jwt.JwtParser
import com.photo.server.starsnap.global.service.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
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
    private val oAuth2SuccessHandler: OAuth2SuccessHandler,
    private val oAuth2UserService: CustomOAuth2UserService,
    private val customAuthenticationFailureHandler: CustomAuthenticationFailureHandler,
    private val jwtFilter: JwtFilter,
    private val loggingFilter: LoggingFilter
) {

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .requestMatchers("/error", "/favicon.ico", "/.well-known/assetlinks.json")
        }
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        authService: AuthService,
        reportService: ReportService
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
                    .hasAnyAuthority(Authority.ADMIN.name, Authority.USER.name)
                authorize.requestMatchers(HttpMethod.POST, "/api/oauth/**").permitAll()

                // auth
                authorize.requestMatchers( HttpMethod.POST,"api/auth/signup").permitAll()
                authorize.requestMatchers( HttpMethod.POST,"api/auth/email/send").permitAll()
                authorize.requestMatchers( HttpMethod.POST,"api/auth/email/verify").permitAll()
                authorize.requestMatchers(HttpMethod.DELETE, "api/auth/secession")
                    .hasAnyAuthority(Authority.ADMIN.name, Authority.USER.name)
                authorize.requestMatchers(HttpMethod.PATCH, "/api/auth/refresh").permitAll()


                // report
                authorize.requestMatchers(HttpMethod.GET, "api/report/**").hasAnyAuthority(Authority.ADMIN.name)
                authorize.requestMatchers(HttpMethod.POST, "api/report/**")
                    .hasAnyAuthority(Authority.ADMIN.name, Authority.USER.name)

                // snap
                authorize.requestMatchers("api/snap/**").hasAnyAuthority(Authority.ADMIN.name, Authority.USER.name)

                // user
                authorize.requestMatchers(HttpMethod.PATCH, "api/user/change-data")
                    .hasAnyAuthority(Authority.ADMIN.name, Authority.USER.name)
                authorize.requestMatchers(HttpMethod.GET, "api/user").permitAll()

                //other
                authorize.requestMatchers(HttpMethod.OPTIONS, "api/**").permitAll()

                // valid
                authorize.requestMatchers(HttpMethod.GET, "/api/auth/valid/username").permitAll()
                authorize.requestMatchers(HttpMethod.GET, "/api/auth/valid/email").permitAll()


            }

            .oauth2Login { oauth -> // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
                oauth.userInfoEndpoint { c ->
                    c.userService(oAuth2UserService)
                }
                    .successHandler(oAuth2SuccessHandler)
                    .failureHandler(customAuthenticationFailureHandler)
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

    @Bean
    protected fun passwordEncoder() = BCryptPasswordEncoder()
}