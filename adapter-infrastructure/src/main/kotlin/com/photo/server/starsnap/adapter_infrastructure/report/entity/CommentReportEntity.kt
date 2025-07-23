package com.photo.server.starsnap.adapter_infrastructure.report.entity

import jakarta.persistence.*
import com.photo.server.starsnap.adapter_infrastructure.report.entity.base.ReportBaseEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.CommentEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.report.entity.CommentReport
import java.time.LocalDateTime

@Entity
@Table(name = "comment_report")
class CommentReportEntity(
    @Column(name = "explanation", nullable = false, updatable = false)
    val explanation: String,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_id", nullable = false, columnDefinition = "CHAR(16)")
    val comment: CommentEntity,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reporter_id", nullable = false, columnDefinition = "CHAR(16)")
    val reporter: UserEntity,
    override var id: String,
    override var createdAt: LocalDateTime,
) : ReportBaseEntity() {

    companion object {
        fun fromDomain(commentReport: CommentReport) = CommentReportEntity(
            explanation = commentReport.explanation,
            comment = CommentEntity.fromDomain(commentReport.comment),
            reporter = UserEntity.fromDomain(commentReport.reporter),
            id = commentReport.id,
            createdAt = commentReport.createdAt
        )
    }
}