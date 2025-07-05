package com.photo.server.starsnap.adapter_web.controller.file

import com.photo.server.starsnap.adapter_usecase.file.usecase.FileUseCaseImpl
import com.photo.server.starsnap.usecase.file.dto.UploadFileRequest
import com.photo.server.starsnap.usecase.file.dto.UploadFileResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/file")
class FileController(
    private val fileUseCaseImpl: FileUseCaseImpl
) {
    @PostMapping("/photo")
    fun createPhotoPresidentUrl(request: UploadFileRequest): ResponseEntity<UploadFileResponse> {
        return ResponseEntity.ok(fileUseCaseImpl.createPhotoPresidentUrl(request))
    }

    @PostMapping("/video")
    fun createVideoPresidentUrl(request: UploadFileRequest): ResponseEntity<UploadFileResponse> {
        return ResponseEntity.ok(fileUseCaseImpl.createVideoPresidentUrl(request))
    }
}