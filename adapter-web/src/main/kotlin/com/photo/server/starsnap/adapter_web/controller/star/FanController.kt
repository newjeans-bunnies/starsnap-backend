package com.photo.server.starsnap.adapter_web.controller.star

import com.photo.server.starsnap.adapter_usecase.star.usecase.FanUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.domain.user.entity.User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/fan")
class FanController(
    private val fanUseCaseImpl: FanUseCaseImpl
) {
    @PostMapping("/join")
    fun joinFan(
        @AuthenticationPrincipalUserData user: User,
        @RequestParam("star-id") starId: String
    ) {
        return fanUseCaseImpl.joinFan(user, starId)
    }

    @PostMapping("/disconnect")
    fun disconnectFan(
        @AuthenticationPrincipalUserData user: User,
        @RequestParam("star-id") starId: String
    ) {
        return fanUseCaseImpl.disconnectFan(user, starId)
    }
}