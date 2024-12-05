package com.photo.server.starsnap.domain.star.controller

import com.photo.server.starsnap.domain.star.service.FandomJoinService
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.global.dto.StatusDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/fandom")
class FandomJoinController(
    private val fandomJoinService: FandomJoinService
) {
    @PostMapping("/join")
    fun joinFandom(
        @AuthenticationPrincipalUserData user: UserEntity, @RequestParam("famdom-id") fandomId: String
    ): StatusDto {
        return fandomJoinService.joinFandom(user, fandomId)
    }

    @PostMapping("/disconnect")
    fun disconnectFandom(
        @AuthenticationPrincipalUserData user: UserEntity, @RequestParam("famdom-id") fandomId: String
    ): StatusDto {
        return fandomJoinService.disconnectFandom(user, fandomId)
    }
}