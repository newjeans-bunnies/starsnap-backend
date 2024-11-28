package com.photo.server.starsnap.domain.star.controller

import com.photo.server.starsnap.domain.star.dto.CreateStarGroupRequestDto
import com.photo.server.starsnap.domain.star.dto.ExistDto
import com.photo.server.starsnap.domain.star.dto.StarGroupResponseDto
import com.photo.server.starsnap.domain.star.dto.UpdateStarGroupRequestDto
import com.photo.server.starsnap.domain.star.service.StarGroupService
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import jakarta.validation.Valid
import org.springframework.data.domain.Slice
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/star-group")
class StarGroupController(
    private val starGroupService: StarGroupService
) {
    @GetMapping("/get")
    fun getStarGroups(
        @AuthenticationPrincipal user: CustomUserDetails,
        @RequestParam page: Int, @RequestParam size: Int
    ): Slice<StarGroupResponseDto> {
        return starGroupService.getStarGroup(size, page)
    }

    @PostMapping("/create")
    fun createStarGroup(
        @Valid @RequestBody starGroupDto: CreateStarGroupRequestDto,
        @AuthenticationPrincipal user: CustomUserDetails
    ): StarGroupResponseDto {
        return starGroupService.createStarGroup(starGroupDto)
    }

    @PatchMapping("/update")
    fun updateStarGroup(
        @Valid @RequestBody starGroupDto: UpdateStarGroupRequestDto,
        @AuthenticationPrincipal user: CustomUserDetails
    ): StarGroupResponseDto {
        return starGroupService.updateStarGroup(starGroupDto)
    }

    @GetMapping("/exist")
    fun existStarGroups(
        @RequestParam type: String,
        @RequestParam name: String,
        @AuthenticationPrincipal user: CustomUserDetails
    ): ExistDto {
        return starGroupService.existStarGroup(type, name)
    }
}