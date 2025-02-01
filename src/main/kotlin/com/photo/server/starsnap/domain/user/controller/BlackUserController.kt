package com.photo.server.starsnap.domain.user.controller

import com.photo.server.starsnap.domain.user.controller.dto.BlackUserDto
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.domain.user.service.BlackUserService
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.global.dto.StatusDto
import org.springframework.data.domain.Slice
import org.springframework.web.bind.annotation.*

@RequestMapping("/api")
@RestController
class BlackUserController(
    private val blackUserService: BlackUserService
) {
    @PostMapping("/black-user")
    fun blackUser(
        @AuthenticationPrincipalUserData userData: UserEntity,
        @RequestParam("black-user-id") blackUserId: String
    ): StatusDto {
        blackUserService.blackUser(userData, blackUserId)
        return StatusDto("차단 완료", 201)
    }

    @DeleteMapping("unblack-user")
    fun unBlackUser(
        @AuthenticationPrincipalUserData userData: UserEntity,
        @RequestParam("unblack-user-id") unBlackUserId: String
    ): StatusDto {
        blackUserService.unBlackUser(userData, unBlackUserId)
        return StatusDto("차단 취소", 204)
    }

    @GetMapping("/black-user")
    fun getBlackUser(
        @AuthenticationPrincipalUserData userData: UserEntity,
        @RequestParam size: Int,
        @RequestParam page: Int
    ): Slice<BlackUserDto>{
        return blackUserService.getBlackUser(size, page, userData)
    }

}