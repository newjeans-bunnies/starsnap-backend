package com.photo.server.starsnap.adapter_web.controller.star

import com.photo.server.starsnap.adapter_usecase.star.usecase.FandomUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.star.dto.CreateFandomRequestDto
import com.photo.server.starsnap.usecase.star.dto.FandomDto
import com.photo.server.starsnap.usecase.star.dto.UpdateFandomRequestDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/fandom/set")
class FandomController(
    private val fandomUseCaseImpl: FandomUseCaseImpl
) {
    @PostMapping("/create")
    fun createFandom(
        @AuthenticationPrincipalUserData user: User,
        @RequestBody fandomDto: CreateFandomRequestDto
    ): FandomDto {
        return fandomUseCaseImpl.createFandom(fandomDto)
    }

    @PatchMapping("/update")
    fun updateFandom(
        @AuthenticationPrincipalUserData user: User,
        @RequestBody fandomDto: UpdateFandomRequestDto
    ): FandomDto {
        return fandomUseCaseImpl.updateFandom(fandomDto)
    }

    @DeleteMapping("/delete")
    fun deleteFandom(
        @AuthenticationPrincipalUserData user: User,
        @RequestParam("fandom-id") fandomId: String
    ) {
        return fandomUseCaseImpl.deleteFandom(fandomId)
    }

}