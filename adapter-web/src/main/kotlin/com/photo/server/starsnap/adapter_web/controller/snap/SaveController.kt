package com.photo.server.starsnap.adapter_web.controller.snap

import com.photo.server.starsnap.adapter_usecase.snap.usecase.SaveUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalId
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/snap")
class SaveController(
    private val saveUseCaseImpl: SaveUseCaseImpl
) {
    @PostMapping("/save")
    fun saveSnap(
        @AuthenticationPrincipalUserData user: User,
        @RequestParam("snap-id") snapId: String
    ): StatusDto {
        return saveUseCaseImpl.saveSnap(user, snapId)
    }

    @DeleteMapping("/un-save")
    fun unSaveSnap(
        @AuthenticationPrincipalId userId: String,
        @RequestParam("snap-id") snapId: String
    ): StatusDto {
        return saveUseCaseImpl.unSaveSnap(userId, snapId)
    }
}