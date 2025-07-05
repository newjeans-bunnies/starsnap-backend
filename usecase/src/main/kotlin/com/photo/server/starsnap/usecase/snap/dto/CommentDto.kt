package com.photo.server.starsnap.usecase.snap.dto

import com.photo.server.starsnap.domain.snap.entity.Comment
import java.time.LocalDateTime

data class CreateCommentRequestDto(
    val content: String,
    val snapId: String
)

data class UpdateCommentRequestDto(
    val commentId: String,
    val content: String,
)

data class CommentDto(
    val profileKey: String?,
    val username: String,
    val content: String,
    val createdAt: LocalDateTime?,
    val modifiedAt: LocalDateTime?
)

fun Comment.toCommentDto() =
    CommentDto(
        this.user.profileImageUrl,
        this.user.username,
        this.content,
        this.createdAt,
        this.modifiedAt
    )