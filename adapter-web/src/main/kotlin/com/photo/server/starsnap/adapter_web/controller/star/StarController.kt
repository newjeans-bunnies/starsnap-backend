package com.photo.server.starsnap.adapter_web.controller.star

import com.photo.server.starsnap.adapter_usecase.star.usecase.StarUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.star.dto.CreateStarRequestDto
import com.photo.server.starsnap.usecase.star.dto.ExistDto
import com.photo.server.starsnap.usecase.star.dto.JoinStarGroupDto
import com.photo.server.starsnap.usecase.star.dto.UpdateStarRequestDto
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/star")
class StarController(
    private val starUseCaseImpl: StarUseCaseImpl,
) {
    @PostMapping("/create")
    fun createStar(
        @Valid @RequestBody starDto: CreateStarRequestDto,
        @AuthenticationPrincipalUserData user: User
    ) {
        starUseCaseImpl.createStar(starDto)
    }

    @PatchMapping("/update")
    fun updateStar(
        @Valid @RequestBody starDto: UpdateStarRequestDto,
        @AuthenticationPrincipalUserData user: User
    ) {
        starUseCaseImpl.updateStar(starDto)
    }

    @GetMapping("/exist")
    fun existStar(
        @RequestParam type: String,
        @RequestParam name: String,
        @AuthenticationPrincipalUserData user: User
    ): ExistDto {
        return starUseCaseImpl.existStar(type, name)
    }

    @PostMapping("/join")
    fun joinStarGroup(
        @Valid @RequestBody joinStarGroup: JoinStarGroupDto,
        @AuthenticationPrincipalUserData user: User
    ): StatusDto {
        return starUseCaseImpl.joinStarGroup(joinStarGroup)
    }
}