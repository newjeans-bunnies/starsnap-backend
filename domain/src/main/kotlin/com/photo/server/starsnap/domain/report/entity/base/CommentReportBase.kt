package com.photo.server.starsnap.domain.report.entity.base

import java.time.LocalDateTime

abstract class CommentReportBase(
    open val id: String,
    open val createdAt: LocalDateTime
)