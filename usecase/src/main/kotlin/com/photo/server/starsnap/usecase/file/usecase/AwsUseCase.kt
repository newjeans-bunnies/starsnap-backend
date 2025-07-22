package com.photo.server.starsnap.usecase.file.usecase

import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.file.dto.UploadFileRequest

interface AwsUseCase {
    fun createPresignedUploadUrl(key: String, metadata: UploadFileRequest, user: User): String
}