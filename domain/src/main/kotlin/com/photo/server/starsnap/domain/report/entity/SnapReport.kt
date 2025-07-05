package com.photo.server.starsnap.domain.report.entity

import com.photo.server.starsnap.domain.report.entity.base.SnapReportBase
import com.photo.server.starsnap.domain.snap.entity.Snap
import com.photo.server.starsnap.domain.user.entity.User
import java.time.LocalDateTime

data class SnapReport(
    val snap: Snap,
    val reporter: User,
    val explanation: String,
    override val id: String,
    override val createdAt: LocalDateTime
) : SnapReportBase(id, createdAt)