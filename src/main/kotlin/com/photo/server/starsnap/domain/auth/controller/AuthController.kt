package com.photo.server.starsnap.domain.auth.controller

import com.photo.server.starsnap.domain.auth.controller.dto.LoginDto
import com.photo.server.starsnap.domain.auth.controller.dto.SignupDto
import com.photo.server.starsnap.domain.auth.controller.dto.TokenDto
import com.photo.server.starsnap.domain.auth.service.AuthService
import com.photo.server.starsnap.domain.auth.service.ReissueTokenService
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService, private val reissueTokenService: ReissueTokenService
) {
    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto): TokenDto {
        return authService.login(loginDto.username, loginDto.password)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signup(@RequestBody @Valid signupDto: SignupDto): StatusDto {
        return authService.signup(signupDto)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    fun deleteUser(@AuthenticationPrincipal auth: CustomUserDetails?) {
        authService.deleteUser()
    }

    @PatchMapping("/refresh")
    fun reissueToken(
        @RequestHeader("refresh-token") refreshToken: String,
        @RequestHeader("access-token") accessToken: String,
    ): TokenDto {
        return reissueTokenService.reissueToken(refreshToken, accessToken)
    }
}