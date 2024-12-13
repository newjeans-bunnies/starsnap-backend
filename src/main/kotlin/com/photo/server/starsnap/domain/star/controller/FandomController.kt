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
@RequestMapping("/api/fandom/set")
class FandomController(
    private val fandomService: FandomService
) {
    @PostMapping("/create")
    fun createFandom(
        @AuthenticationPrincipalUserData user: UserEntity,
        @RequestBody fandomDto: CreateFandomRequestDto
    ): FandomDto {
        return fandomService.createFandom(fandomDto)
    }

    @PatchMapping("/update")
    fun updateFandom(
        @AuthenticationPrincipalUserData user: UserEntity,
        @RequestBody fandomDto: UpdateFandomRequestDto
    ): FandomDto {
        return fandomService.updateFandom(fandomDto)
    }

    @DeleteMapping("/delete")
    fun deleteFandom(
        @AuthenticationPrincipalUserData user: UserEntity,
        @RequestParam("fandom-id") fandomId: String
    ) {
        return fandomService.deleteFandom(fandomId)
    }

}