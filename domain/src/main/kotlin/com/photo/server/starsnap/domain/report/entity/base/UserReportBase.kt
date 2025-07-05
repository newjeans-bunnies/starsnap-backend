package com.photo.server.starsnap.domain.report.entity.base

import java.time.LocalDateTime

abstract class UserReportBase(
    open val id: String,
    open val createdAt: LocalDateTime
)