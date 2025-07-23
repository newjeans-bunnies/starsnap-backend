package com.photo.server.starsnap.adapter_web.controller.file

import com.photo.server.starsnap.adapter_usecase.file.usecase.FileUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.file.dto.UploadFileRequest
import com.photo.server.starsnap.usecase.file.dto.UploadFileResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/file")
class FileController(
    private val fileUseCaseImpl: FileUseCaseImpl,
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)
    @PostMapping("/photo")
    fun createPhotoPresidentUrl(
        @RequestBody request: UploadFileRequest,
        @AuthenticationPrincipalUserData user: User
    ): ResponseEntity<UploadFileResponse> {
        logger.info(user.toString())
        return ResponseEntity.ok(fileUseCaseImpl.createPhotoPresidentUrl(request, user))
    }

    @PostMapping("/video")
    fun createVideoPresidentUrl(
        @RequestBody request: UploadFileRequest,
        @AuthenticationPrincipalUserData user: User
    ): ResponseEntity<UploadFileResponse> {
        return ResponseEntity.ok(fileUseCaseImpl.createVideoPresidentUrl(request, user))
    }
}