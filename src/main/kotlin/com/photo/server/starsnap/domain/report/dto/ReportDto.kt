package com.photo.server.starsnap.domain.report.dto

import com.photo.server.starsnap.domain.report.entity.SnapReportEntity
import com.photo.server.starsnap.domain.report.UserReportEntity
import com.photo.server.starsnap.domain.snap.SnapEntity
import com.photo.server.starsnap.domain.user.UserEntity
import org.springframework.data.domain.Slice

data class SnapReportCreateDto(
    val explanation: String,
    val snapId: String
)

data class UserReportCreateDto(
    val explanation: String,
    val userId: String
)

data class SnapReportDto(
    val id: String,
    val reporter: UserEntity,
    val snap: SnapEntity,
    val createdAt: String
)

fun Slice<SnapReportEntity>.toSnapReportDto() = this.map {
    SnapReportDto(
        id = it.id,
        reporter = it.reporter,
        snap = it.snap,
        createdAt = it.createdAt
    )
}

data class UserReportDto(
    val id: String,
    val reporter: UserEntity,
    val user: UserEntity,
    val createdAt: String
)

fun Slice<UserReportEntity>.toUserReportDto() = this.map {
    UserReportDto(
        id = it.id,
        reporter = it.reporter,
        user = it.user,
        createdAt = it.createdAt
    )
}