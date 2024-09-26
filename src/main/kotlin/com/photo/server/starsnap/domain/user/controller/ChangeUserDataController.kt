package com.photo.server.starsnap.domain.user.controller

import com.photo.server.starsnap.domain.user.service.UserAwsS3Service
import com.photo.server.starsnap.domain.user.service.UserService
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalId
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.dto.UserDto
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Validated
@RestController
@RequestMapping("/api/user/change-data")
class ChangeUserDataController(
    private val userService: UserService,
    private val userAwsS3Service: UserAwsS3Service
) {

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/username")
    fun changeUsername(
        @NotBlank(message = "닉네임은 필수 입력 값입니다.") @Pattern(
            regexp = "^[a-zA-Z0-9]{4,12}$", message = "닉네임은 4~12자 영문 대 소문자, 숫자만 사용하세요."
        ) @Valid @RequestParam username: String, @AuthenticationPrincipal auth: CustomUserDetails
    ): UserDto {
        val userData = userService.changeUsername(username, auth.username)
        return userData
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/profile-image")
    fun changeProfileImage(
        @AuthenticationPrincipalId userId: String,
        @Valid @RequestParam image: MultipartFile
    ): StatusDto {
        userAwsS3Service.changeProfileImage(userId, image)
        return StatusDto("OK", 200)
    }

}