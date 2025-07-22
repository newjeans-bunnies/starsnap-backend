package com.photo.server.starsnap.usecase.file.dto

data class UploadFileResponse(
    val presignedUrl: String
)

data class UploadFileRequest(
    val aiState: Boolean,
    val dateTaken: String,
    val source: String
)