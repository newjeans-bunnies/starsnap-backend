package com.photo.server.starsnap.adapter_web.controller.star

import com.photo.server.starsnap.adapter_usecase.star.usecase.FandomJoinUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/fandom")
class FandomJoinController(
    private val fandomJoinUseCaseImpl: FandomJoinUseCaseImpl
) {
    @PostMapping("/join")
    fun joinFandom(
        @AuthenticationPrincipalUserData user: User,
        @RequestParam("famdom-id") fandomId: String
    ): StatusDto {
        return fandomJoinUseCaseImpl.joinFandom(user, fandomId)
    }

    @PostMapping("/disconnect")
    fun disconnectFandom(
        @AuthenticationPrincipalUserData user: User,
        @RequestParam("famdom-id") fandomId: String
    ): StatusDto {
        return fandomJoinUseCaseImpl.disconnectFandom(user, fandomId)
    }
}