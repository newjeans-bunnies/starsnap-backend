package com.photo.server.starsnap.domain.star.controller

import com.photo.server.starsnap.domain.star.service.FanService
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalUserData
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/fan")
class FanController(
    private val fanService: FanService
) {
    @PostMapping("/join")
    fun joinFan(
        @AuthenticationPrincipalUserData user: UserEntity,
        @RequestParam("star-id") starId: String
    ) {
        return fanService.joinFan(user.id, starId)
    }

    @PostMapping("/disconnect")
    fun disconnectFan(
        @AuthenticationPrincipalUserData user: UserEntity,
        @RequestParam("star-id") starId: String
    ) {
        return fanService.disconnectFan(user.id, starId)
    }
}