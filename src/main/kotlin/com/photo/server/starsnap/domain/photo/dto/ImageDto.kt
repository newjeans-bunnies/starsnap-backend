package com.photo.server.starsnap.domain.photo.dto

import com.photo.server.starsnap.domain.photo.ImageEntity

data class ImageDto(
    val createdAt: String,
    val createdBy: String,
    val url: String
)

fun ImageEntity.toImageDto() = ImageDto(
    createdAt = this.createdAt,
    createdBy = this.createdBy,
    url = this.url
)