package com.photo.server.starsnap.domain.star.controller

import com.photo.server.starsnap.domain.star.dto.*
import com.photo.server.starsnap.domain.star.service.StarService
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import com.photo.server.starsnap.global.service.AwsS3Service
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("api/star")
class StarController(
    private val starService: StarService,
    private val awsS3Service: AwsS3Service,
) {
    @PostMapping("/create")
    fun createStar(@Valid @RequestBody starDto: CreateStarRequestDto, @AuthenticationPrincipal user: CustomUserDetails) {
        starService.createStar(starDto)
    }

    @PatchMapping("/update")
    fun updateStar(@Valid @RequestBody starDto: UpdateStarRequestDto, @AuthenticationPrincipal user: CustomUserDetails) {
        starService.updateStar(starDto)
    }

    @GetMapping("/exist")
    fun existStar(
        @RequestParam type: String,
        @RequestParam name: String,
        @AuthenticationPrincipal user: CustomUserDetails
    ): ExistDto {
        return starService.existStar(type, name)
    }

    @PostMapping("/join")
    fun joinStarGroup(
        @Valid @RequestBody joinStarGroup: JoinStarGroupDto,
        @AuthenticationPrincipal user: CustomUserDetails
    ): StatusDto {
        return starService.joinStarGroup(joinStarGroup)
    }

    @PostMapping("image/upload")
    fun uploadImage(
        @RequestPart("image") image: MultipartFile, // 사진
        @AuthenticationPrincipal user: CustomUserDetails
    ): StarImageResponseDto {
        val imageKey = awsS3Service.uploadFileToS3(image, "star", user.user.id)
        return StarImageResponseDto(imageKey)
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
        @RequestPart("image-key") imageKey: String,
        @AuthenticationPrincipal user: CustomUserDetails
    ): StatusDto {
        awsS3Service.deleteFileTos3(imageKey)
        return StatusDto("OK", 200)
    }
}