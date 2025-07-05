package com.photo.server.starsnap.usecase.file.dto

import java.time.LocalDateTime

data class UploadFileResponse(
    val presignedUrl: String
)

data class UploadFileRequest(
    val aiState: Boolean,
    val dateTaken: LocalDateTime,
    val source: String
)