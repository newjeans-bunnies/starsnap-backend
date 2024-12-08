package com.photo.server.starsnap.domain.snap.controller

import com.photo.server.starsnap.domain.snap.service.LikeService
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.global.dto.StatusDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/snap/like")
class LikeController(
    private val likeService: LikeService
) {
    @PostMapping
    fun likeSnap(
        @AuthenticationPrincipalUserData user: UserEntity,
        @RequestParam("snap-id") snapId: String
    ): StatusDto {
        return likeService.likeSnap(user, snapId)
    }

    @DeleteMapping
    fun unlikeSnap(
        @AuthenticationPrincipalUserData user: UserEntity,
        @RequestParam("snap-id") snapId: String
    ): StatusDto {
        return likeService.unlikeSnap(user, snapId)
    }
}