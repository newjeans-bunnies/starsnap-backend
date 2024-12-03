package com.photo.server.starsnap.domain.star.controller

import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.star.service.FandomService
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalUserData
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/fandom")
class FandomController(
    private val fandomService: FandomService
) {
    @PostMapping("/create")
    fun createFandom(
        @AuthenticationPrincipalUserData user: UserEntity,
    ) {
        if (user.authority != Authority.ADMIN) throw RuntimeException("권한 없음")
        return fandomService.createFandom()
    }

    @PatchMapping("/update")
    fun updateFandom(
        @AuthenticationPrincipalUserData user: UserEntity,
    ) {
        if (user.authority != Authority.ADMIN) throw RuntimeException("권한 없음")
        return fandomService.updateFandom()
    }

    @DeleteMapping("/delete")
    fun deleteFandom(
        @AuthenticationPrincipalUserData user: UserEntity,
    ) {
        if (user.authority != Authority.ADMIN) throw RuntimeException("권한 없음")
        return fandomService.deleteFandom()
    }

    @PostMapping("/join")
    fun joinFandom() {
        return fandomService.joinFandom()
    }

    @PostMapping("/disconnect")
    fun disconnectFandom() {
        return fandomService.disconnectFandom()
    }
}