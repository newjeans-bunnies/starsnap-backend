package com.photo.server.starsnap.domain.snap.controller

import com.photo.server.starsnap.domain.snap.dto.SnapDto
import com.photo.server.starsnap.domain.snap.service.SnapService
import com.photo.server.starsnap.global.config.BucketConfig
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.error.exception.TooManyRequestException
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.data.domain.Slice
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

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
        @RequestPart("image") image: MultipartFile, // 사진
        @RequestPart("title") title: String, // 글
        @RequestPart("source") source: String, // 사진 출처
        @RequestPart("date-taken") dateTaken: String // 사진 찍은 날짜
    ) {
        if(!bucketConfig.createSnapBucket().tryConsume(1)) throw TooManyRequestException

        snapService.createSnap(
            userData = user.getUserData(),
            title = title,
            image = image,
            source = source,
            dateTaken = dateTaken
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/send")
    fun sendSnap(@RequestParam page: Int, @RequestParam size: Int): Slice<SnapDto> {
        return snapService.sendSnap(size, page)
    }

    @PatchMapping("/fix")
    fun fixSnap(
        @RequestPart("snap-id") snapId: String,
        @RequestPart("source") source: String,
        @RequestPart("title") title: String,
        @RequestPart("date-taken") dateTaken: String,
        @RequestPart(name = "image", required = false) image: MultipartFile?,
        @AuthenticationPrincipal user: CustomUserDetails
    ): SnapDto {
        return snapService.fixSnap(user.username, snapId, image, source, title, dateTaken)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    fun deleteSnap(
        @RequestParam("snap-id") snapId: String,
        @AuthenticationPrincipal user: CustomUserDetails
    ): StatusDto {
        snapService.deleteSnap(user.username, snapId)
        return StatusDto("Ok", 200)
    }

}