package com.photo.server.starsnap.domain.star.controller

import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.star.dto.CreateStarRequestDto
import com.photo.server.starsnap.domain.star.dto.ExistDto
import com.photo.server.starsnap.domain.star.dto.JoinStarGroupDto
import com.photo.server.starsnap.domain.star.dto.UpdateStarRequestDto
import com.photo.server.starsnap.domain.star.service.StarService
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/star")
class StarController(
    private val starService: StarService
) {
    @PostMapping("/create")
    fun createStar(@Valid @RequestBody starDto: CreateStarRequestDto, @AuthenticationPrincipal user: CustomUserDetails) {
        if (user.authority != Authority.ADMIN) throw RuntimeException("권한 없음")
        starService.createStar(starDto)
    }

    @PatchMapping("/update")
    fun updateStar(@Valid @RequestBody starDto: UpdateStarRequestDto, @AuthenticationPrincipal user: CustomUserDetails) {
        if (user.authority != Authority.ADMIN) throw RuntimeException("권한 없음")
        starService.updateStar(starDto)
    }

    @GetMapping("/exist")
    fun existStar(
        @RequestParam type: String,
        @RequestParam name: String,
        @AuthenticationPrincipal user: CustomUserDetails
    ): ExistDto {
        if (user.authority != Authority.ADMIN) throw RuntimeException("권한 없음")
        return starService.existStar(type, name)
    }

    @PostMapping("/join")
    fun joinStarGroup(
        @Valid @RequestBody joinStarGroup: JoinStarGroupDto,
        @AuthenticationPrincipal user: CustomUserDetails
    ): StatusDto {
        if (user.authority != Authority.ADMIN) throw RuntimeException("권한 없음")
        return starService.joinStarGroup(joinStarGroup)
    }
}