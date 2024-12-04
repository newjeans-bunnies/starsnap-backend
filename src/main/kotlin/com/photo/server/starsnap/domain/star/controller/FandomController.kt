package com.photo.server.starsnap.domain.star.controller

import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.star.dto.CreateFandomRequestDto
import com.photo.server.starsnap.domain.star.dto.FandomDto
import com.photo.server.starsnap.domain.star.dto.UpdateFandomRequestDto
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
        @RequestBody fandomDto: CreateFandomRequestDto
    ): FandomDto {
        if (user.authority != Authority.ADMIN) throw RuntimeException("권한 없음")
        return fandomService.createFandom(fandomDto)
    }

    @PatchMapping("/update")
    fun updateFandom(
        @AuthenticationPrincipalUserData user: UserEntity,
        @RequestBody fandomDto: UpdateFandomRequestDto
    ): FandomDto {
        if (user.authority != Authority.ADMIN) throw RuntimeException("권한 없음")
        return fandomService.updateFandom(fandomDto)
    }

    @DeleteMapping("/delete")
    fun deleteFandom(
        @AuthenticationPrincipalUserData user: UserEntity,
        @RequestParam("fandom-id") fandomId: String
    ) {
        if (user.authority != Authority.ADMIN) throw RuntimeException("권한 없음")
        return fandomService.deleteFandom(fandomId)
    }

    @PostMapping("/join")
    fun joinFandom(
        @AuthenticationPrincipalUserData user: UserEntity, @RequestParam("famdom-id") fandomId: String
    ) {
        return fandomService.joinFandom(user.id, fandomId)
    }

    @PostMapping("/disconnect")
    fun disconnectFandom(
        @AuthenticationPrincipalUserData user: UserEntity, @RequestParam("famdom-id") fandomId: String
    ) {
        return fandomService.disconnectFandom(user.id, fandomId)
    }
}