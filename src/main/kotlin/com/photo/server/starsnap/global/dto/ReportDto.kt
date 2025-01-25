package com.photo.server.starsnap.global.dto

import com.photo.server.starsnap.domain.snap.dto.CommentDto
import java.time.LocalDateTime

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
