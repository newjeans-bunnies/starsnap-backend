package com.photo.server.starsnap.domain.report.entity

import com.photo.server.starsnap.domain.report.entity.base.UserReportBase
import com.photo.server.starsnap.domain.user.entity.User
import java.time.LocalDateTime

data class UserReport(
    val explanation: String,
    val reporter: User,
    val defendant: User,
    override val createdAt: LocalDateTime,
    override val id: String
) : UserReportBase(id, createdAt)