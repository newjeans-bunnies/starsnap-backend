package com.photo.server.starsnap.domain.user.controller

import com.photo.server.starsnap.domain.user.service.FollowService
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/auth")
@RestController
class FollowController(
    private val followService: FollowService
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/follow")
    fun follow(@AuthenticationPrincipal auth: CustomUserDetails, @RequestParam("user-id") userId: String) {
        followService.follow(auth.username, userId)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/unfollow")
    fun unfollow(@AuthenticationPrincipal auth: CustomUserDetails, @RequestParam("user-id") userId: String) {
        followService.unFollow(auth.username, userId)
    }
}