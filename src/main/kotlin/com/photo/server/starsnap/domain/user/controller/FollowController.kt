package com.photo.server.starsnap.domain.user.controller

import com.photo.server.starsnap.domain.user.entity.FollowEntity
import com.photo.server.starsnap.domain.user.service.FollowService
import com.photo.server.starsnap.global.config.BucketConfig
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import org.springframework.data.domain.Slice
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/user")
@RestController
class FollowController(
    private val followService: FollowService,
    private val bucketConfig: BucketConfig
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/follow")
    fun follow(@AuthenticationPrincipal auth: CustomUserDetails, @RequestParam("user-id") userId: String): StatusDto {
        if(!bucketConfig.followBucket().tryConsume(1))
        followService.follow(auth.username, userId)

        return StatusDto("OK", 200)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/unfollow")
    fun unfollow(@AuthenticationPrincipal auth: CustomUserDetails, @RequestParam("user-id") userId: String): StatusDto {
        if(!bucketConfig.followBucket().tryConsume(1))
        followService.unFollow(auth.username, userId)

        return StatusDto("OK", 200)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/follow")
    fun getFollow(
        @AuthenticationPrincipal auth: CustomUserDetails,
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): Slice<FollowEntity> {
        return followService.getFollow(auth.username, page, size)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/follower")
    fun getFollower(
        @AuthenticationPrincipal auth: CustomUserDetails,
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): Slice<FollowEntity> {
        return followService.getFollower(auth.username, page, size)
    }

}