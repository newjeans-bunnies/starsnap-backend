package com.photo.server.starsnap.usecase.report.dto

import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import com.photo.server.starsnap.domain.report.entity.CommentReport
import com.photo.server.starsnap.domain.report.entity.SnapReport
import com.photo.server.starsnap.domain.report.entity.UserReport
import com.photo.server.starsnap.usecase.snap.dto.CommentDto
import com.photo.server.starsnap.usecase.snap.dto.SnapDto
import com.photo.server.starsnap.usecase.snap.dto.toCommentDto
import com.photo.server.starsnap.usecase.snap.dto.toSnapDto
import com.photo.server.starsnap.usecase.user.dto.UserDto
import com.photo.server.starsnap.usecase.user.dto.toUserDto
import java.time.LocalDateTime


data class SnapReportCreateDto(
    val explanation: String,
    val snapId: String
)

data class UserReportCreateDto(
    val explanation: String,
    val userId: String
)

data class CommentReportCreateDto(
    val explanation: String,
    val commentId: String
)

data class UserReportDto(
    val id: String,
    val createdAt: LocalDateTime,
    var explanation: String,
    val reporter: UserDto,
    val defendant: UserDto
)

data class SnapReportDto(
    val id: String,
    val createdAt: LocalDateTime,
    var explanation: String,
    val reporter: UserDto,
    val snap: SnapDto
)

data class CommentReportDto(
    val id: String,
    val createdAt: LocalDateTime,
    val explanation: String,
    val reporter: UserDto,
    val comment: CommentDto
)

fun Slice<SnapReport>.toSnapReportDto(): Slice<SnapReportDto> = this.map {
        SnapReportDto(
        id = it.id,
        createdAt = it.createdAt,
        explanation = it.explanation,
        reporter = it.reporter.toUserDto(),
        snap = it.snap.toSnapDto()
    )
}



fun Slice<UserReport>.toUserReportDto(): Slice<UserReportDto> = this.map {
    UserReportDto(
        id = it.id,
        createdAt = it.createdAt,
        explanation = it.explanation,
        reporter = it.reporter.toUserDto(),
        defendant = it.defendant.toUserDto()
    )
}

fun Slice<CommentReport>.toCommentReportDto(): Slice<CommentReportDto> = this.map {
    CommentReportDto(
        id = it.id,
        createdAt = it.createdAt,
        explanation = it.explanation,
        reporter = it.reporter.toUserDto(),
        comment = it.comment.toCommentDto()
    )
}