package com.photo.server.starsnap.domain.report.dto

import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.report.entity.SnapReportEntity
import com.photo.server.starsnap.domain.report.entity.UserReportEntity
import com.photo.server.starsnap.global.utils.type.TYPE
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
    val createdAt: String,
    var explanation: String,
    val reporterId: String,
    val reporterUsername: String,
    val reporterEmail: String,
    val reporterAuthority: String,
    val snapId: String,
    val snapTitle: String,
    val snapCreatedAt: String,
    val snapSize: Long,
    val snapType: TYPE,
    val snapSource: String,
    val snapImageKey: String,
    val snapDateTaken: String,
    val snapImageWidth: Int,
    val snapImageHeight: Int
)

fun Slice<SnapReportEntity>.toSnapReportDto(): Slice<SnapReportDto> = this.map {
    SnapReportDto(
        id = it.id,
        createdAt = it.createdAt,
        explanation = it.explanation,
        reporterId = it.reporterId,
        reporterUsername = it.reporterUsername,
        reporterEmail = it.reporterEmail,
        reporterAuthority = it.reporterAuthority,
        snapId = it.snapId,
        snapTitle = it.snapTitle,
        snapCreatedAt = it.snapCreatedAt,
        snapSize = it.snapSize,
        snapType = it.snapType,
        snapImageKey = it.snapImageKey,
        snapSource = it.snapSource,
        snapImageWidth = it.snapImageWidth,
        snapImageHeight = it.snapImageHeight,
        snapDateTaken = it.snapDateTaken
    )
}

data class UserReportDto(
    val id: String,
    val reporterId: String,
    val reporterUsername: String,
    val reporterEmail: String,
    val reporterAuthority: String,
    val defendantId: String,
    val defendantUsername: String,
    val defendantEmail: String,
    val defendantAuthority: Authority,
    val createdAt: String
)

fun Slice<UserReportEntity>.toUserReportDto(): Slice<UserReportDto> = this.map {
    UserReportDto(
        id = it.id,
        reporterId = it.reporterId,
        reporterUsername = it.reporterUsername,
        reporterEmail = it.reporterEmail,
        reporterAuthority = it.reporterAuthority,
        defendantId = it.defendantId,
        defendantUsername = it.defendantUsername,
        defendantEmail = it.defendantEmail,
        defendantAuthority = it.defendantAuthority,
        createdAt = it.createdAt
    )
}