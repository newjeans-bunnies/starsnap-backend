package com.photo.server.starsnap.domain.star.controller

import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.star.dto.CreateStarGroupDto
import com.photo.server.starsnap.domain.star.dto.ExistDto
import com.photo.server.starsnap.domain.star.dto.UpdateStarGroupDto
import com.photo.server.starsnap.domain.star.service.StarGroupService
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class StarGroupController(
    private val starGroupService: StarGroupService
) {
    @PostMapping
    fun createStarGroup(
        @Valid @RequestBody starGroupDto: CreateStarGroupDto,
        @AuthenticationPrincipal user: CustomUserDetails
    ) {
        if(user.authority != Authority.ADMIN) throw RuntimeException("권한 없음")
        starGroupService.createStarGroup(starGroupDto)
    }

    @PatchMapping
    fun updateStarGroup(
        @Valid @RequestBody starGroupDto: UpdateStarGroupDto,
        @AuthenticationPrincipal user: CustomUserDetails
    ) {
        if(user.authority != Authority.ADMIN) throw RuntimeException("권한 없음")
        starGroupService.updateStarGroup(starGroupDto)
    }

    @GetMapping
    fun existStarGroups(@RequestParam type: String, @RequestParam name: String): ExistDto {
        return starGroupService.existStarGroup(type, name )
    }
}