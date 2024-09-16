package com.photo.server.starsnap.domain.user.controller

import com.photo.server.starsnap.domain.user.service.UserService
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService
) {

    @PatchMapping("change-username")
    fun changeUsername(
        @NotBlank(message = "닉네임은 필수 입력 값입니다.") @Pattern(
            regexp = "^[a-zA-Z0-9]{4,12}$", message = "닉네임은 4~12자 영문 대 소문자, 숫자만 사용하세요."
        ) @Valid @RequestParam username: String, @AuthenticationPrincipal auth: CustomUserDetails
    ): StatusDto {
        userService.changeUsername(username, auth.username)
        return StatusDto("OK", 200)
    }

}