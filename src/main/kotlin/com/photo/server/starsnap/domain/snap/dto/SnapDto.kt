package com.photo.server.starsnap.domain.snap.dto

import com.photo.server.starsnap.domain.snap.SnapEntity

data class SnapDto(
    val snapId: String,
    val title: String,
    val createdAt: String,
    val username: String,
    val imageKey: String,
    val source: String,
    val type: String,
    val size: Long,
    val dateTaken: String
)


fun SnapEntity.toSnapData(): SnapDto {
    return SnapDto(
        snapId = this.id,
        title = this.title,
        createdAt = this.createdAt,
        username = this.userId.username,
        imageKey = this.imageKey,
        source = this.source,
        type = this.type.name,
        size = this.size,
        dateTaken = this.dateTaken
    )
}
