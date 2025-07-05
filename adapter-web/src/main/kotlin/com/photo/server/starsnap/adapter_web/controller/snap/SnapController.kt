package com.photo.server.starsnap.adapter_web.controller.snap

import com.photo.server.starsnap.adapter_infrastructure.global.security.principle.CustomUserDetails
import com.photo.server.starsnap.adapter_usecase.snap.usecase.LikeUseCaseImpl
import com.photo.server.starsnap.adapter_usecase.snap.usecase.SnapUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.config.BucketConfig
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.exception.global.error.exception.TooManyRequestException
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.snap.dto.CreateSnapRequestDto
import com.photo.server.starsnap.usecase.snap.dto.CreateSnapResponseDto
import com.photo.server.starsnap.usecase.snap.dto.GetSnapResponseDto
import com.photo.server.starsnap.usecase.snap.dto.SnapResponseDto
import com.photo.server.starsnap.usecase.snap.dto.UpdateSnapRequestDto
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/snap")
class SnapController(
    private val snapUseCaseImpl: SnapUseCaseImpl,
    private val likeUseCaseImpl: LikeUseCaseImpl,
    private val bucketConfig: BucketConfig
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    fun createSnap(
        @AuthenticationPrincipal user: CustomUserDetails,
        @RequestPart @Valid snapDto: CreateSnapRequestDto,
    ): ResponseEntity<CreateSnapResponseDto> {
        if (!bucketConfig.createSnapBucket().tryConsume(1)) throw TooManyRequestException

        val snap = snapUseCaseImpl.createSnap(
            userData = user.getUserData(),
            snapDto = snapDto,
        )

        logger.info(snap.toString())

        return ResponseEntity.status(HttpStatus.CREATED).body(snap)
    }

    // 테스트 용
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    fun getAllSnap(
    ): List<SnapResponseDto> {
        val snapData = snapUseCaseImpl.getAllSnap()
        return snapData
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    fun getSnap(
        @RequestBody getSnapResponseDto: GetSnapResponseDto,
        @AuthenticationPrincipalUserData user: User,
    ): Slice<SnapResponseDto> {
        val snapData = snapUseCaseImpl.getSnap(getSnapResponseDto, user)
        return snapData
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/update")
    fun updateSnap(
        @ModelAttribute @Valid snapDto: UpdateSnapRequestDto,
        @AuthenticationPrincipalUserData user: User,
    ): SnapResponseDto {
        val snapData = snapUseCaseImpl.updateSnap(snapDto, user)
        return snapData
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/delete")
    fun deleteSnap(
        @RequestParam("snap-id") snapId: String,
        @AuthenticationPrincipalUserData user: User
    ): StatusDto {
        snapUseCaseImpl.deleteSnap(user, snapId)
        return StatusDto("Ok", 200)
    }

    @PostMapping("/like")
    fun likeSnap(
        @AuthenticationPrincipalUserData user: User,
        @RequestParam("snap-id") snapId: String
    ): StatusDto {
        return likeUseCaseImpl.snapLikeSnap(user, snapId)
    }

    @DeleteMapping("/unlike")
    fun unlikeSnap(
        @AuthenticationPrincipalUserData user: User,
        @RequestParam("snap-id") snapId: String
    ): StatusDto {
        return likeUseCaseImpl.snapUnlikeSnap(user, snapId)
    }


}