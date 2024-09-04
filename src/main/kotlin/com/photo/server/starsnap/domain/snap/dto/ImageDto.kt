package com.photo.server.starsnap.domain.snap.dto

import com.photo.server.starsnap.domain.snap.ImageEntity
import com.photo.server.starsnap.domain.snap.type.TYPE
import com.photo.server.starsnap.domain.user.UserEntity
import org.springframework.web.multipart.MultipartFile

data class ImageDto(
    val createdAt: String,
    val createdBy: String,
    val url: String,
    val size: Long,
    val type: TYPE,
    val source: String
)

data class CreateImageDto(
    val createdBy: UserEntity,
    val image: MultipartFile,
    val source: String,
    val dateTaken: String
)

fun ImageEntity.toImageDto() = ImageDto(
    createdAt = this.createdAt,
    createdBy = this.createdBy.username,
    url = this.id,
    size = this.size,
    type = this.type,
    source = this.source
)