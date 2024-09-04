package com.photo.server.starsnap.domain.snap.dto

import com.photo.server.starsnap.domain.snap.SnapEntity

data class SnapDto(
    val snapId: String,
    val title: String,
    val createdAt: String,
    val username: String,
    val imageDto: ImageDto
)


fun SnapEntity.toSnapData(): SnapDto {
    return SnapDto(
        snapId = this.id,
        title = this.title,
        createdAt = this.createdAt,
        username = this.createdBy.username,
        imageDto = this.image.toImageDto()
    )
}
