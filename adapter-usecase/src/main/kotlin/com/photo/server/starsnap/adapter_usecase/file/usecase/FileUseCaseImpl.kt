package com.photo.server.starsnap.adapter_usecase.file.usecase

import com.photo.server.starsnap.usecase.file.dto.UploadFileRequest
import com.photo.server.starsnap.usecase.file.dto.UploadFileResponse
import com.photo.server.starsnap.usecase.file.usecase.FileUseCase
import io.viascom.nanoid.NanoId
import org.springframework.stereotype.Service

@Service
class FileUseCaseImpl(
    private val awsUseCaseImpl: AwsUseCaseImpl
): FileUseCase {
    override fun createPhotoPresidentUrl(request: UploadFileRequest): UploadFileResponse {
        val path = "photo/${NanoId.generate(32, "_-0123456789abcdefghijklmnopqrstuvwxyz")}"
        val presignedUrl = awsUseCaseImpl.createPresignedUploadUrl(path, request)
        return UploadFileResponse(presignedUrl)
    }

    override fun createVideoPresidentUrl(request: UploadFileRequest): UploadFileResponse {
        val path = "videos/${NanoId.generate(32, "_-0123456789abcdefghijklmnopqrstuvwxyz")}"
        val presignedUrl = awsUseCaseImpl.createPresignedUploadUrl(path, request)
        return UploadFileResponse(presignedUrl)
    }
}