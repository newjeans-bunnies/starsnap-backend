package com.photo.server.starsnap

import com.photo.server.starsnap.domain.auth.dto.EmailDto
import com.photo.server.starsnap.domain.auth.dto.LoginDto
import com.photo.server.starsnap.domain.auth.dto.SignupDto
import com.photo.server.starsnap.domain.auth.service.AuthService
import com.photo.server.starsnap.domain.auth.service.EmailService
import com.photo.server.starsnap.domain.auth.service.ReissueTokenService
import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.user.controller.dto.ChangePasswordDto
import com.photo.server.starsnap.domain.user.service.FollowService
import com.photo.server.starsnap.global.security.jwt.JwtProvider
import io.viascom.nanoid.NanoId
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import kotlin.test.Test

@SpringBootTest
class StarSnapApplicationTests {

    @Autowired
    private lateinit var jwtProvider: JwtProvider

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @Autowired
    private lateinit var authService: AuthService

    @Autowired
    private lateinit var emailService: EmailService

    @Autowired
    private lateinit var followService: FollowService

    @Autowired
    private lateinit var reissueTokenService: ReissueTokenService

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
        val password =
            "fjwepdsfnsofen2uhefwidsufhdisfausdjnfesfdsfdsfjdsifjldsjfosifndlfjldskifldsfiksjfnosfjmsdjkfnewofnefj"

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
    @DisplayName("인증 코드 확인")
    fun verify(){
        val email = "ghkdwndhks@gmail.com"
        val verifyCode = "953024"
        val requestData = EmailDto(email, verifyCode)

        val responseData = emailService.verify(requestData)

        println(responseData.token)
    }

    @Test
    @DisplayName("회원가입")
    fun signup() {
        val requestData = SignupDto(
            username = "test",
            password = "test1234!",
            email = "ghkdwndhks@gmail.com",
            token = "XuqvzT0SG_n_"
        )

        val responseData = authService.signup(requestData)
    }

    @Test
    @DisplayName("로그인")
    fun login() {
        val requestData = LoginDto(
            username = "test",
            password = "test1234!"
        )

        val responseData = authService.login(requestData.username, requestData.password)
    }

    @Test
    @DisplayName("유저 삭제")
    fun deleteUser() {
        val requestData = ""

        val responseData = authService.deleteUser(requestData)
    }

    @Test
    @DisplayName("토큰 재발급")
    fun refreshToken() {
        val accessToken = ""
        val refreshToken = ""

        val responseData = reissueTokenService.reissueToken(refreshToken, accessToken)
    }

    @Test
    @DisplayName("비밀번호 변경")
    fun changePassword() {
        val requestData = ChangePasswordDto(
            username = "test",
            password = "test1234!",
            newPassword = "test1234.",
        )

        val responseData = authService.changePassword(requestData)
    }


    @Test
    @DisplayName("팔로워 가져오기")
    fun getFollower() {
        val userId: String = ""
        val size: Int = 10
        val page: Int = 0

        val responseData = followService.getFollowers(userId, page, size)
    }

    @Test
    @DisplayName("팔로우 하기")
    fun follow() {
        val userId: String = ""
        val followUserId: String = ""

        val responseData = followService.follow(userId, followUserId)
    }

    @Test
    @DisplayName("팔로우 가져오기")
    fun getFollowing() {
        val userId: String = ""
        val size: Int = 10
        val page: Int = 0

        val responseData = followService.getFollowing(userId, page, size)
    }

}
