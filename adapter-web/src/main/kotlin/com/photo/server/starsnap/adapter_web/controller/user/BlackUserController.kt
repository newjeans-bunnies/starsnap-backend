package com.photo.server.starsnap.adapter_web.controller.user

import com.photo.server.starsnap.adapter_usecase.user.usecase.BlackUserUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.user.dto.BlackUserDto
import org.springframework.web.bind.annotation.*

@RequestMapping("/api")
@RestController
class BlackUserController(
    private val blackUserUseCaseImpl: BlackUserUseCaseImpl
) {
    @PostMapping("/black-user")
    fun blackUser(
        @AuthenticationPrincipalUserData userData: User,
        @RequestParam("black-user-id") blackUserId: String
    ): StatusDto {
        blackUserUseCaseImpl.blackUser(userData, blackUserId)
        return StatusDto("차단 완료", 201)
    }

    @DeleteMapping("unblack-user")
    fun unBlackUser(
        @AuthenticationPrincipalUserData userData: User,
        @RequestParam("unblack-user-id") unBlackUserId: String
    ): StatusDto {
        blackUserUseCaseImpl.unBlackUser(userData, unBlackUserId)
        return StatusDto("차단 취소", 204)
    }

    @GetMapping("/black-user")
    fun getBlackUser(
        @AuthenticationPrincipalUserData userData: User,
        @RequestParam size: Int,
        @RequestParam page: Int
    ): Slice<BlackUserDto> {
        return blackUserUseCaseImpl.getBlackUser(size, page, userData)
    }

}