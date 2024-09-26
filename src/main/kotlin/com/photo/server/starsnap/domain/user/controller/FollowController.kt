package com.photo.server.starsnap.domain.user.controller

import com.photo.server.starsnap.domain.user.controller.dto.FollowerDto
import com.photo.server.starsnap.domain.user.controller.dto.FollowingDto
import com.photo.server.starsnap.domain.user.service.FollowService
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalId
import com.photo.server.starsnap.global.config.BucketConfig
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.error.exception.TooManyRequestException
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
    fun follow(
        @AuthenticationPrincipal userId: String,
        @RequestParam("follow-user-id") followUserId: String
    ): StatusDto {
        if (!bucketConfig.followBucket().tryConsume(1)) throw TooManyRequestException

        followService.follow(userId, followUserId)

        return StatusDto("OK", 200)
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/unfollow")
    fun unfollow(
        @AuthenticationPrincipalId userId: String,
        @RequestParam("follow-id") followId: String
    ): StatusDto {
        if (!bucketConfig.followBucket().tryConsume(1)) throw TooManyRequestException

        followService.unFollow(userId, followId)

        return StatusDto("OK", 200)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/follow")
    fun getFollow(
        @AuthenticationPrincipalId userId: String,
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): Slice<FollowingDto> {
        if (!bucketConfig.getFollowData().tryConsume(1)) throw TooManyRequestException

        val followData = followService.getFollowing(userId, page, size)
        return followData
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/follower")
    fun getFollower(
        @AuthenticationPrincipalId userId: String,
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): Slice<FollowerDto> {
        if (!bucketConfig.getFollowData().tryConsume(1)) throw TooManyRequestException

        val followerData = followService.getFollowers(userId, page, size)
        return followerData
    }

}