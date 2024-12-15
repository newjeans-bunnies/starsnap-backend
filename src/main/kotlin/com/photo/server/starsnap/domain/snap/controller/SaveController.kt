package com.photo.server.starsnap.domain.snap.controller

import com.photo.server.starsnap.domain.snap.service.SaveService
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalId
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.global.dto.StatusDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/snap")
class SaveController(
    private val saveService: SaveService
) {
    @PostMapping("/save")
    fun saveSnap(
        @AuthenticationPrincipalUserData user: UserEntity,
        @RequestParam("snap-id") snapId: String
    ): StatusDto {
        return saveService.saveSnap(user, snapId)
    }

    @DeleteMapping("/un-save")
    fun unSaveSnap(
        @AuthenticationPrincipalId userId: String,
        @RequestParam("snap-id") snapId: String
    ): StatusDto {
        return saveService.unSaveSnap(userId, snapId)
    }
}