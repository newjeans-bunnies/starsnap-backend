package com.photo.server.starsnap.usecase.file.dto

import com.photo.server.starsnap.domain.file.entity.Photo
import com.photo.server.starsnap.domain.file.type.PhotoType
import java.time.LocalDateTime

data class PhotoDto(
    val fileKey: String,
    val type: PhotoType,
    val source: String,
    val aiState: Boolean,
    val dateTaken: LocalDateTime,
    val fileSize: Long
)

fun Photo.toPhotoDto() = PhotoDto(
    fileKey = fileKey,
    type = photoType,
    source = source,
    dateTaken = dateTaken,
    fileSize = fileSize,
    aiState = aiState,
)