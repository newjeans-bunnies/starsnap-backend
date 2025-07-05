package com.photo.server.starsnap.usecase.file.usecase

import com.photo.server.starsnap.usecase.file.dto.UploadFileRequest
import com.photo.server.starsnap.usecase.file.dto.UploadFileResponse

interface FileUseCase {
    fun createPhotoPresidentUrl(
        request: UploadFileRequest
    ): UploadFileResponse

    fun createVideoPresidentUrl(
        request: UploadFileRequest
    ): UploadFileResponse
}