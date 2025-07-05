package com.photo.server.starsnap.domain.report.entity

import com.photo.server.starsnap.domain.report.entity.base.CommentReportBase
import com.photo.server.starsnap.domain.snap.entity.Comment
import com.photo.server.starsnap.domain.user.entity.User
import java.time.LocalDateTime


data class CommentReport(
    val explanation: String,
    val comment: Comment,
    val reporter: User,
    override val createdAt: LocalDateTime,
    override val id: String
): CommentReportBase(id, createdAt)