package com.photo.server.starsnap.domain.user.controller

import com.photo.server.starsnap.domain.user.service.UserAwsS3Service
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
import org.springframework.web.multipart.MultipartFile

@Validated
@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService,
    private val userAwsS3Service: UserAwsS3Service
) {
    @GetMapping
    fun getUserData(@RequestParam("user-id") userId: String): UserDto {
        val userData = userService.getUserData(userId)
        return userData
    }

}