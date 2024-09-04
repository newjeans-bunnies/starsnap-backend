package com.photo.server.starsnap.domain.snap.controller

import com.photo.server.starsnap.domain.snap.dto.CreateImageDto
import com.photo.server.starsnap.domain.snap.dto.SnapDto
import com.photo.server.starsnap.domain.snap.service.ImageService
import com.photo.server.starsnap.domain.snap.service.SnapService
import com.photo.server.starsnap.global.dto.StatusDto
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
    private val imageService: ImageService
) {
    private val logger = LoggerFactory.getLogger(javaClass)


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    fun createSnap(
        @AuthenticationPrincipal user: CustomUserDetails,
        @RequestPart("image") image: MultipartFile,
        @RequestPart("title") title: String,
        @RequestPart("source") source: String,
        @RequestPart("date-taken") dateTaken: String
    ) {
        val imageData = imageService.createImage(CreateImageDto(user.getUserData(), image, source, dateTaken))
        snapService.createSnap(userData = user.getUserData(), title = title, imageData = imageData)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/send")
    fun sendSnap(@RequestParam page: Int, @RequestParam size: Int): Slice<SnapDto> {
        return snapService.sendSnap(page, size)
    }

    @PatchMapping("/fix")
    fun fixSnap(
        @RequestPart("snap-id") snapId: String,
        @RequestPart("image-id") imageId: String,
        @RequestPart("user-id") userId: String,
        @RequestPart("source") source: String,
        @RequestPart("title") title: String,
        @RequestPart("date-taken") dateTaken: String,
        @RequestPart(name = "image", required = false) image: MultipartFile?,
        @AuthenticationPrincipal user: CustomUserDetails
    ): SnapDto {
        imageService.fixImage(image, imageId, source, dateTaken)
        return snapService.fixSnap(snapId, user.username, title)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    fun deleteSnap(
        @RequestParam("snap-id") snapId: String,
        @RequestParam("image-id") imageId: String,
        @AuthenticationPrincipal user: CustomUserDetails
    ): StatusDto {
        imageService.deleteImage(user.username, imageId)
        snapService.deleteSnap(user.username, snapId)
        return StatusDto("Ok", 200)
    }

}