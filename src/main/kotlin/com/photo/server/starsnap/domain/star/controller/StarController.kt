package com.photo.server.starsnap.domain.star.controller

import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.star.dto.CreateStarDto
import com.photo.server.starsnap.domain.star.dto.ExistDto
import com.photo.server.starsnap.domain.star.dto.FixStarDto
import com.photo.server.starsnap.domain.star.service.StarService
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/star")
class StarController(
    private val starService: StarService
) {
    @PostMapping
    fun createStar(@Valid @RequestBody starDto: CreateStarDto, @AuthenticationPrincipal user: CustomUserDetails) {
        if(user.authority != Authority.ADMIN) throw RuntimeException("권한 없음")
        starService.createStar(starDto)
    }

    @PatchMapping
    fun fixStar(@Valid @RequestBody starDto: FixStarDto, @AuthenticationPrincipal user: CustomUserDetails) {
        if(user.authority != Authority.ADMIN) throw RuntimeException("권한 없음")
        starService.fixStar(starDto)
    }



    @GetMapping("/exist")
    fun existStar(@RequestParam type: String, @RequestParam name: String): ExistDto {
        return starService.existStar(type ,name)
    }
}