package com.photo.server.starsnap.adapter_web.controller.star

import com.photo.server.starsnap.adapter_usecase.star.usecase.StarGroupUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.star.dto.CreateStarGroupRequestDto
import com.photo.server.starsnap.usecase.star.dto.ExistDto
import com.photo.server.starsnap.usecase.star.dto.StarGroupResponseDto
import com.photo.server.starsnap.usecase.star.dto.UpdateStarGroupRequestDto
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/star-group")
class StarGroupController(
    private val starGroupUseCaseImpl: StarGroupUseCaseImpl,
) {
    @GetMapping("/get")
    fun getStarGroups(
        @AuthenticationPrincipalUserData user: User,
        @RequestParam page: Int, @RequestParam size: Int
    ): Slice<StarGroupResponseDto> {
        return starGroupUseCaseImpl.getStarGroup(size, page)
    }

    @PostMapping("/create")
    fun createStarGroup(
        @Valid @RequestBody starGroupDto: CreateStarGroupRequestDto,
        @AuthenticationPrincipalUserData user: User
    ): StarGroupResponseDto {
        return starGroupUseCaseImpl.createStarGroup(starGroupDto)
    }

    @PatchMapping("/update")
    fun updateStarGroup(
        @Valid @RequestBody starGroupDto: UpdateStarGroupRequestDto,
        @AuthenticationPrincipalUserData user: User
    ): StarGroupResponseDto {
        return starGroupUseCaseImpl.updateStarGroup(starGroupDto)
    }

    @GetMapping("/exist")
    fun existStarGroups(
        @RequestParam type: String,
        @RequestParam name: String,
        @AuthenticationPrincipalUserData user: User
    ): ExistDto {
        return starGroupUseCaseImpl.existStarGroup(type, name)
    }
}