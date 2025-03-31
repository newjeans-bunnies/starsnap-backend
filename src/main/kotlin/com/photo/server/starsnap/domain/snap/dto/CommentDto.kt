package com.photo.server.starsnap.domain.snap.dto

import com.photo.server.starsnap.domain.snap.entity.CommentEntity
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime

data class CreateCommentRequestDto(
    @field:NotBlank(message = "내용은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "내용은 1~500자 영문 대 소문자, 숫자만 사용하세요.")
    val content: String,
    @field:NotBlank(message = "snap Id는 필수 입력 값입니다.")
    @field:Pattern(regexp = "^[\\-_0-9a-zA-Z]{16}$", message = "16자 영문 대 소문자, 숫자, -,_")
    val snapId: String
)

data class UpdateCommentRequestDto(
    @field:NotBlank(message = "댓글 아이디는 필수 입력 값입니다.")
    val commentId: String,
    @field:NotBlank(message = "내용은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "내용은 1~500자 영문 대 소문자, 숫자만 사용하세요.")
    val content: String,
)

data class CommentDto(
    val profileKey: String?,
    val username: String,
    val content: String,
    val createdAt: LocalDateTime?,
    val modifiedAt: LocalDateTime?
)

fun CommentEntity.toCommentDto() =
    CommentDto(this.user.profileImageUrl, this.user.username, this.content, this.createdAt, this.modifiedAt)