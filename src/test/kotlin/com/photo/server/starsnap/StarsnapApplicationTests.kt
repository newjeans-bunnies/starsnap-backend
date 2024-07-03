package com.photo.server.starsnap

import com.photo.server.starsnap.domain.auth.service.AuthService
import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.global.security.jwt.JwtProvider
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import kotlin.test.Test

@SpringBootTest
class StarsnapApplicationTests {

    @Autowired
    private lateinit var jwtProvider: JwtProvider

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @Autowired
    private lateinit var authService: AuthService

    @Test
    @DisplayName("Token 생성")
    fun contextLoads() {
        println("test")
        val id = "test"
        val authority = Authority.USER
        val token = jwtProvider.receiveToken(id, authority)

        println("refreshToken: " + token.refreshToken)
        println("accessToken: " + token.accessToken)
    }

    @Test
    @DisplayName("password hash")
    fun passwordHash() {
        val password = "fjwepdsfnsofen2uhefwidsufhdisfausdjnfesfdsfdsfjdsifjldsjfosifndlsjfnosfjmsdjkfnewofnefj"

        val hashPassword = passwordEncoder.encode(password)

        println(hashPassword)

        println("글자수 : ${hashPassword.length}")
    }


    @Test
    @DisplayName("SQL injection")
    fun SQLInjection() {
        val username: String = "a' OR 1==1 --"
        val password: String = "a"
        val token = authService.login(username, password)
        println(token)
    }

}
