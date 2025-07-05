package com.photo.server.starsnap.adapter_web.controller.user

import com.photo.server.starsnap.adapter_usecase.user.usecase.FollowUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.config.BucketConfig
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.exception.global.error.exception.TooManyRequestException
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.user.dto.FollowerDto
import com.photo.server.starsnap.usecase.user.dto.FollowingDto
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/user")
@RestController
class FollowController(
    private val followUseCaseImpl: FollowUseCaseImpl,
    private val bucketConfig: BucketConfig
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/follow")
    fun follow(
        @AuthenticationPrincipal user: User,
        @RequestParam("follow-user-id") followUserId: String
    ): StatusDto {
        if (!bucketConfig.followBucket().tryConsume(1)) throw TooManyRequestException

        followUseCaseImpl.follow(user, followUserId)

        return StatusDto("OK", 200)
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/unfollow")
    fun unfollow(
        @AuthenticationPrincipal user: User,
        @RequestParam("follow-user-id") followUserId: String
    ): StatusDto {
        if (!bucketConfig.followBucket().tryConsume(1)) throw TooManyRequestException

        followUseCaseImpl.unFollow(user, followUserId)

        return StatusDto("OK", 200)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/follow")
    fun getFollow(
        @AuthenticationPrincipalUserData user: User,
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): Slice<FollowingDto> {
        if (!bucketConfig.getFollowData().tryConsume(1)) throw TooManyRequestException

        val followData = followUseCaseImpl.getFollowing(user, page, size)
        return followData
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/follower")
    fun getFollower(
        @AuthenticationPrincipalUserData user: User,
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int
    ): Slice<FollowerDto> {
        if (!bucketConfig.getFollowData().tryConsume(1)) throw TooManyRequestException

        val followerData = followUseCaseImpl.getFollower(user, page, size)
        return followerData
    }

}