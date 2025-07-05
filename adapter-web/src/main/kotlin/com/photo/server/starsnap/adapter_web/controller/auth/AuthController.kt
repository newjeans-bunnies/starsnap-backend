package com.photo.server.starsnap.adapter_web.controller.auth

import com.photo.server.starsnap.adapter_usecase.auth.usecase.AuthUseCaseImpl
import com.photo.server.starsnap.adapter_usecase.auth.usecase.EmailCaseImpl
import com.photo.server.starsnap.adapter_usecase.auth.usecase.ReissueTokenUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.config.BucketConfig
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.exception.global.error.exception.TooManyRequestException
import com.photo.server.starsnap.usecase.auth.dto.LoginDto
import com.photo.server.starsnap.usecase.auth.dto.SignupDto
import com.photo.server.starsnap.usecase.auth.dto.TokenDto
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.user.dto.ChangePasswordDto
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/auth")
class AuthController(
    private val authUseCaseImpl: AuthUseCaseImpl,
    private val reissueTokenUseCaseImpl: ReissueTokenUseCaseImpl,
    private val emailUseCaseImpl: EmailCaseImpl,
    private val bucketConfig: BucketConfig
) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    fun login(@RequestBody @Valid loginDto: LoginDto): TokenDto {
        if (!bucketConfig.loginBucket().tryConsume(1)) throw TooManyRequestException
        val tokenDto = authUseCaseImpl.login(loginDto.username, loginDto.password)
        return tokenDto
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signup(@RequestBody @Valid signupDto: SignupDto): StatusDto {
        if (!bucketConfig.signupBucket().tryConsume(1)) throw TooManyRequestException
        emailUseCaseImpl.checkValidVerifyCode(signupDto.token, signupDto.email)
        authUseCaseImpl.signup(signupDto)
        emailUseCaseImpl.deleteToken(signupDto.token, signupDto.email)
        return StatusDto("Signup successfully", 201)
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/pw-set")
    fun setPassword(@RequestParam password: String, @AuthenticationPrincipalUserData user: User): StatusDto {
        authUseCaseImpl.setPassword(password, user)
        return StatusDto("Password successfully changed", 201)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/secession")
    fun deleteUser(@AuthenticationPrincipalUserData user: User): StatusDto {
        if (!bucketConfig.deleteUserBucket().tryConsume(1)) throw TooManyRequestException
        authUseCaseImpl.deleteUser(user)
        return StatusDto("Deleted successfully", 201)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/user/rollback")
    fun userRollback(@RequestBody @Valid loginDto: LoginDto): TokenDto {
        return authUseCaseImpl.userRollback(loginDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/refresh")
    fun reissueToken(
        @RequestHeader("refresh-token") refreshToken: String, @RequestHeader("access-token") accessToken: String
    ): TokenDto {
        if (!bucketConfig.reissueTokenBucket().tryConsume(1)) throw TooManyRequestException

        val tokenDto = reissueTokenUseCaseImpl.reissueToken(refreshToken, accessToken)
        return tokenDto
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/pw-change")
    fun changePassword(
        @RequestBody @Valid changePasswordDto: ChangePasswordDto
    ): StatusDto {
        if (!bucketConfig.changePasswordBucket().tryConsume(1)) throw TooManyRequestException

        authUseCaseImpl.changePassword(changePasswordDto)
        return StatusDto("Password changed successfully", 200)
    }

}