package com.photo.server.starsnap.domain.report.dto

import com.photo.server.starsnap.domain.report.entity.SnapReportEntity
import com.photo.server.starsnap.domain.report.entity.UserReportEntity
import com.photo.server.starsnap.global.dto.SnapReportDto
import com.photo.server.starsnap.global.dto.UserReportDto
import com.photo.server.starsnap.global.dto.toSnapDto
import com.photo.server.starsnap.global.dto.toUserDto
import org.springframework.data.domain.Slice

data class SnapReportCreateDto(
    val explanation: String,
    val snapId: String
)

data class UserReportCreateDto(
    val explanation: String,
    val userId: String
)

fun Slice<SnapReportEntity>.toSnapReportDto(): Slice<SnapReportDto> = this.map {
    SnapReportDto(
        id = it.id,
        createdAt = it.createdAt,
        explanation = it.explanation,
        reporter = it.reporter.toUserDto(),
        snap = it.snap.toSnapDto()
    )
}

fun Slice<UserReportEntity>.toUserReportDto(): Slice<UserReportDto> = this.map {
    UserReportDto(
        id = it.id,
        createdAt = it.createdAt,
        explanation = it.explanation,
        reporter = it.reporter.toUserDto(),
        defendant = it.defendant.toUserDto()
    )
}