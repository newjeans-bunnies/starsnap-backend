package com.photo.server.starsnap.domain.auth.controller

import com.photo.server.starsnap.domain.auth.controller.dto.LoginDto
import com.photo.server.starsnap.domain.auth.controller.dto.SignupDto
import com.photo.server.starsnap.domain.auth.controller.dto.TokenDto
import com.photo.server.starsnap.domain.auth.service.AuthService
import com.photo.server.starsnap.domain.auth.service.EmailService
import com.photo.server.starsnap.domain.auth.service.ReissueTokenService
import com.photo.server.starsnap.domain.user.controller.dto.ChangePasswordDto
import com.photo.server.starsnap.global.config.BucketConfig
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.error.exception.TooManyRequestException
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val reissueTokenService: ReissueTokenService,
    private val emailService: EmailService,
    private val bucketConfig: BucketConfig
) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    fun login(@RequestBody @Valid loginDto: LoginDto): TokenDto {
        if(!bucketConfig.loginBucket().tryConsume(1)) throw TooManyRequestException
        val tokenDto = authService.login(loginDto.username, loginDto.password)
        return tokenDto
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signup(@RequestBody @Valid signupDto: SignupDto): StatusDto {
        if(!bucketConfig.signupBucket().tryConsume(1)) throw TooManyRequestException
        emailService.checkValidVerifyCode(signupDto.token, signupDto.email)
        authService.signup(signupDto)
        emailService.deleteToken(signupDto.token, signupDto.email)
        return StatusDto("Created", 201)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    fun deleteUser(@AuthenticationPrincipal auth: CustomUserDetails): StatusDto {
        if(!bucketConfig.deleteUserBucket().tryConsume(1)) throw TooManyRequestException
        authService.deleteUser(auth.username)
        return StatusDto("Deleted successfully", 201)
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/refresh")
    fun reissueToken(
        @RequestHeader("refresh-token") refreshToken: String,
        @RequestHeader("access-token") accessToken: String
    ): TokenDto {
        if(!bucketConfig.reissueTokenBucket().tryConsume(1)) throw TooManyRequestException

        val tokenDto = reissueTokenService.reissueToken(refreshToken, accessToken)
        return tokenDto
    }

    @PatchMapping("pw-change")
    fun changePassword(
        @RequestBody @Valid changePasswordDto: ChangePasswordDto
    ): StatusDto {
        if(!bucketConfig.changePasswordBucket().tryConsume(1)) throw TooManyRequestException

        authService.changePassword(changePasswordDto)
        return StatusDto("Password changed", 200)
    }

}