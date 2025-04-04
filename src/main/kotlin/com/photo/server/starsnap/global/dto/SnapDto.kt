package com.photo.server.starsnap.global.dto

import com.photo.server.starsnap.domain.snap.dto.CommentDto
import com.photo.server.starsnap.domain.snap.dto.toCommentDto
import com.photo.server.starsnap.domain.snap.entity.SnapEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import java.time.LocalDateTime

data class SnapDto(
    val snapId: String,
    val title: String,
    val createdAt: LocalDateTime?,
    val imageKey: String,
    val source: String,
    val tags: List<String>,
    val type: String,
    val size: Long,
    val dateTaken: LocalDateTime,
    val comments: List<CommentDto>
)

data class SnapUserDto(
    val username: String,
    val imageKey: String?
)

fun UserEntity.toSnapUserDto() = SnapUserDto(
    username = this.username,
    imageKey = this.profileImageUrl
)

fun SnapEntity.toSnapDto() = SnapDto(
    snapId = this.id,
    title = this.title,
    createdAt = this.createdAt,
    imageKey = this.imageKey,
    source = this.source,
    size = this.imageSize,
    dateTaken = this.dateTaken,
    type = this.imageType.name,
    tags = this.tags.map { it.name },
    comments = this.comments.map {
        it.toCommentDto()
    }
)