package com.photo.server.starsnap.adapter_infrastructure.report

import com.photo.server.starsnap.adapter_infrastructure.report.entity.CommentReportEntity
import com.photo.server.starsnap.adapter_infrastructure.report.entity.SnapReportEntity
import com.photo.server.starsnap.adapter_infrastructure.report.entity.UserReportEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.SnapMapper.toComment
import com.photo.server.starsnap.adapter_infrastructure.snap.SnapMapper.toSnap
import com.photo.server.starsnap.adapter_infrastructure.user.UserMapper.toUser
import com.photo.server.starsnap.domain.report.entity.CommentReport
import com.photo.server.starsnap.domain.report.entity.SnapReport
import com.photo.server.starsnap.domain.report.entity.UserReport

object ReportMapper {
    fun CommentReportEntity.toCommentReport() = CommentReport(
        id = this.id,
        createdAt = this.createdAt,
        explanation = this.explanation,
        comment = this.comment.toComment(),
        reporter = this.reporter.toUser()
    )

    fun UserReportEntity.toUserReport() = UserReport(
        id = this.id,
        createdAt = this.createdAt,
        explanation = this.explanation,
        reporter = this.reporter.toUser(),
        defendant = this.defendant.toUser()
    )

    fun SnapReportEntity.toSnapReport() = SnapReport(
        id = this.id,
        createdAt = this.createdAt,
        explanation = this.explanation,
        snap = this.snap.toSnap(),
        reporter = this.reporter.toUser()
    )
}