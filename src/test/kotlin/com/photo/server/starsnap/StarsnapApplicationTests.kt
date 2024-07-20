package com.photo.server.starsnap

import com.photo.server.starsnap.domain.auth.repository.RefreshTokenRepository
import com.photo.server.starsnap.domain.auth.service.AuthService
import com.photo.server.starsnap.domain.auth.service.EmailService
import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.global.security.jwt.JwtProvider
import io.viascom.nanoid.NanoId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
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

    @Autowired
    private lateinit var emailService: EmailService

    @Autowired
    private lateinit var refreshTokenRepository: RefreshTokenRepository

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

    @Test
    @DisplayName("email patten")
    fun emailPatten() {
        val email = "ghkdwndhks@gmail.com"
        val regex = "^[a-zA-Z0-9+-_.]+@[0-9a-zA-Z]+\\.[a-zA-Z]{2,3}\$"

        println(email.matches(regex.toRegex()))
    }

    @Test
    @DisplayName("password patten")
    fun passwordPatten() {
        val password = ""
        val regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\d)(?=.*[~․!@#\$%^&*()_\\-+=|\\\\;:‘“<>,.?/]).{8,50}\$"
        println(password.matches(regex.toRegex()))
    }

    @Test
    @DisplayName("인증 코드 생성")
    fun createVerifyCode() {
        println(NanoId.generate(6, "1234567890"))
    }

    @Test
    @DisplayName("인증 코드 발송")
    fun sendVerifyCode() {
        val email = "ghkdwndhks@gmail.com"
        emailService.send(email)
    }

    @Test
    @DisplayName("find Token")
    fun findToken() {
        val token = "I5OuAejjPRd5"
        val token2 = "eyJKV1QiOiJyZWZyZXNoIiwiYXV0aG9yaXR5IjoiVVNFUiIsImFsZyI6IkhTMjU2In0.eyJpYXQiOjE3MjEwOTk3OTMsImV4cCI6MTcyMjA5OTc5M30.QqZ_h4z4E3aZAVShQ1t9UmBfJNCiBTyr9TTlUHjh_cg"

        val data1 = refreshTokenRepository.findByIdOrNull(token)
        val data2 = refreshTokenRepository.findByToken(token2)


        assertEquals(data1, data2)
    }

}
