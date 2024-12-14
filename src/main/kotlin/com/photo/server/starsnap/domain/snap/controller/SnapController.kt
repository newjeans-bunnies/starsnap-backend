package com.photo.server.starsnap.domain.snap.controller

import com.photo.server.starsnap.domain.snap.dto.CreateSnapRequestDto
import com.photo.server.starsnap.domain.snap.dto.SnapResponseDto
import com.photo.server.starsnap.domain.snap.dto.UpdateSnapRequestDto
import com.photo.server.starsnap.domain.snap.service.SnapService
import com.photo.server.starsnap.global.config.BucketConfig
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.error.exception.TooManyRequestException
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.data.domain.Slice
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/snap")
class SnapController(
    private val snapService: SnapService,
    private val bucketConfig: BucketConfig
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    fun createSnap(
        @AuthenticationPrincipal user: CustomUserDetails,
        @ModelAttribute @Valid snapDto: CreateSnapRequestDto,
    ): StatusDto {
        if(!bucketConfig.createSnapBucket().tryConsume(1)) throw TooManyRequestException

        snapService.createSnap(
            userData = user.getUserData(),
            snapDto = snapDto,
        )

        return StatusDto("created snap", 201)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get")
    fun getSnap(@RequestParam page: Int, @RequestParam size: Int, @RequestParam(required = false, defaultValue = "") tag: String): Slice<SnapResponseDto> {
        val snapData = snapService.getSnap(size, page, tag)
        return snapData
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/update")
    fun updateSnap(
        @ModelAttribute @Valid snapDto: UpdateSnapRequestDto,
        @AuthenticationPrincipal user: CustomUserDetails
    ): SnapResponseDto {
        val snapData = snapService.updateSnap(user.userId, snapDto)
        return snapData
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/delete")
    fun deleteSnap(
        @RequestParam("snap-id") snapId: String,
        @AuthenticationPrincipal user: CustomUserDetails
    ): StatusDto {
        snapService.deleteSnap(user.username, snapId)
        return StatusDto("Ok", 200)
    }

}