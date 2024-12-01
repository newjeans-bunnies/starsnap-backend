package com.photo.server.starsnap.domain.star.controller

import com.photo.server.starsnap.domain.star.dto.*
import com.photo.server.starsnap.domain.star.service.StarGroupService
import com.photo.server.starsnap.domain.star.service.StarImageService
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import jakarta.validation.Valid
import org.springframework.data.domain.Slice
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/star-group")
class StarGroupController(
    private val starGroupService: StarGroupService,
    private val starImageService: StarImageService
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

    @PostMapping("image/upload")
    fun uploadImage(
        @RequestPart("image") image: MultipartFile, // 사진
    ): StarGroupImageResponseDto {
        val imageKey =  starImageService.uploadImage(image, "star-group")
        return StarGroupImageResponseDto(imageKey)
    }

    @PostMapping("image/update")
    fun updateImage(
        @RequestPart("image") image: MultipartFile, // 사진
        @RequestPart("image-key") imageKey: String
    ): StatusDto {
        starImageService.updateImage(image, imageKey)
        return StatusDto("OK", 200)
    }

    @PatchMapping("image/delete")
    fun deleteImage(
        @RequestPart("image-key") imageKey: String
    ): StatusDto {
        starImageService.deleteImage(imageKey)
        return StatusDto("OK", 200)
    }
}