package com.photo.server.starsnap.usecase.snap.dto

import com.photo.server.starsnap.domain.snap.entity.Snap
import com.photo.server.starsnap.usecase.file.dto.PhotoDto
import com.photo.server.starsnap.usecase.file.dto.toPhotoDto
import java.io.InputStream
import java.time.LocalDateTime

data class SnapDto(
    val snapId: String,
    val title: String,
    val createdAt: LocalDateTime?,
    val tags: List<String>,
    val photos: List<PhotoDto>,
    val comments: List<CommentDto>
)

data class SnapUserDto(
    val username: String,
    val imageKey: String?
)

data class SnapResponseDto(
    val createdUser: SnapUserDto,
    val snapData: SnapDto
)

data class CreateSnapRequestDto(
    val title: String,
    val description: String,
    val tags: List<String> = listOf(),
    val starIds: List<String> = listOf(),
    val starGroupIds: List<String> = listOf()
)

data class CreateSnapResponseDto(
    val title: String,
    val tags: List<String>,
    val photos: List<PhotoDto>,
    val createdAt: String
)

data class CreateMetadataDto(
    val aiState: Boolean,
    val dateTaken: LocalDateTime,
    val source: String
)

data class UpdateSnapRequestDto(
    val snapId: String,
    val image: InputStream,
    val title: String,
    val tags: List<String>,
    val starIds: List<String>,
    val starGroupIds: List<String>
)


data class GetSnapResponseDto(
    val size: Int,
    val page: Int,
    val tag: List<String>,
    val title: String,
    val user: String?,
    val starId: List<String>,
    val starGroupId: List<String>,
    val comments: List<CommentDto>
)

data class GetSnapPhotoResponseDto(
    val presignedUrl: String
)

fun Snap.toCreateSnapResponseDto() = CreateSnapResponseDto(
    title = this.title,
    tags = this.tags.map {
        it.name
    },
    photos = this.photos.map { it.toPhotoDto() },
    createdAt = this.createdAt.toString()
)

fun Snap.toSnapDto() = SnapDto(
    snapId = this.id,
    title = this.title,
    createdAt = this.createdAt,
    tags = this.tags.map { it.name },
    photos = this.photos.map { it.toPhotoDto() },
    comments = this.comments.map { it.toCommentDto() }
)