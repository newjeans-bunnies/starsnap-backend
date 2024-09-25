package com.photo.server.starsnap.domain.user.controller

import com.photo.server.starsnap.domain.user.controller.dto.FollowerDto
import com.photo.server.starsnap.domain.user.controller.dto.FollowingDto
import com.photo.server.starsnap.domain.user.entity.FollowEntity
import com.photo.server.starsnap.domain.user.service.FollowService
import com.photo.server.starsnap.global.config.BucketConfig
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.error.exception.TooManyRequestException
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
        if(!bucketConfig.followBucket().tryConsume(1)) throw TooManyRequestException

        followService.follow(auth.username, userId)

        return StatusDto("OK", 200)
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/unfollow")
    fun unfollow(@AuthenticationPrincipal auth: CustomUserDetails, @RequestParam("follow-id") followId: String): StatusDto {
        if(!bucketConfig.followBucket().tryConsume(1)) throw TooManyRequestException

        followService.unFollow(auth.username, followId)

        return StatusDto("OK", 200)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/follow")
    fun getFollow(
        @AuthenticationPrincipal auth: CustomUserDetails,
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): Slice<FollowingDto> {
        if(!bucketConfig.getFollowData().tryConsume(1)) throw TooManyRequestException

        val followData = followService.getFollowing(auth.username, page, size)
        return followData
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/follower")
    fun getFollower(
        @AuthenticationPrincipal auth: CustomUserDetails,
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): Slice<FollowerDto> {
        if(!bucketConfig.getFollowData().tryConsume(1)) throw TooManyRequestException

        val followerData = followService.getFollowers(auth.username, page, size)
        return followerData
    }

}