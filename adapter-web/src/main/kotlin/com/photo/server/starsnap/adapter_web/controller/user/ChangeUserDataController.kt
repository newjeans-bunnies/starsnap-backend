package com.photo.server.starsnap.adapter_web.controller.user

import com.photo.server.starsnap.adapter_usecase.user.usecase.UserUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.user.dto.UserDto
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/user/update")
class ChangeUserDataController(
    private val userUseCaseImpl: UserUseCaseImpl,
) {

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/username")
    fun changeUsername(
        @NotBlank(message = "닉네임은 필수 입력 값입니다.") @Pattern(
            regexp = "^[a-zA-Z0-9]{4,12}$", message = "닉네임은 4~12자 영문 대 소문자, 숫자만 사용하세요."
        ) @Valid @RequestParam username: String,
        @AuthenticationPrincipalUserData user: User
    ): UserDto {
        val userData = userUseCaseImpl.changeUsername(username, user)
        return userData
    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @PatchMapping("/profile-image")
//    fun changeProfileImage(
//        @AuthenticationPrincipalId userId: String,
//        @Valid @RequestParam image: MultipartFile
//    ): StatusDto {
//        awsS3Service.uploadFileToS3(image, "/profile/$userId", userId)
//        return StatusDto("OK", 200)
//    }

}