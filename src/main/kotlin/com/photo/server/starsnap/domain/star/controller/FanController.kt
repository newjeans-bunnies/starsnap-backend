package com.photo.server.starsnap.domain.star.controller

import com.photo.server.starsnap.domain.star.service.FanService
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalUserData
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/fan")
class FanController(
    private val fanService: FanService
) {
    @PostMapping("/join")
    fun joinFan(
        @AuthenticationPrincipalUserData user: UserEntity,
    ) {
        return fanService.joinFan()
    }

    @PostMapping("/disconnect")
    fun disconnectFan(
        @AuthenticationPrincipalUserData user: UserEntity,
    ) {
        return fanService.disconnectFan()
    }
}