package com.photo.server.starsnap.domain.star.controller

import com.photo.server.starsnap.domain.star.dto.*
import com.photo.server.starsnap.domain.star.service.StarGroupService
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import com.photo.server.starsnap.global.service.AwsS3Service
import jakarta.validation.Valid
import org.springframework.data.domain.Slice
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/star-group")
class StarGroupController(
    private val starGroupService: StarGroupService,
    private val awsS3Service: AwsS3Service
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
        @AuthenticationPrincipal user: CustomUserDetails
    ): StarGroupImageResponseDto {
        val imageKey =  awsS3Service.uploadFileToS3(image, "star-group", user.user.id)
        return StarGroupImageResponseDto(imageKey)
    }

    @PostMapping("image/update")
    fun updateImage(
        @RequestPart("image") image: MultipartFile, // 사진
        @RequestPart("image-key") imageKey: String,
        @AuthenticationPrincipal user: CustomUserDetails
    ): StatusDto {
        awsS3Service.uploadFileToS3(image, imageKey, user.user.id)
        return StatusDto("OK", 200)
    }

    @PatchMapping("image/delete")
    fun deleteImage(
        @RequestPart("image-key") imageKey: String
    ): StatusDto {
        awsS3Service.deleteFileTos3(imageKey)
        return StatusDto("OK", 200)
    }
}