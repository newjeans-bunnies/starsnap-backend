package com.photo.server.starsnap.adapter_infrastructure.report.entity

import jakarta.persistence.*
import com.photo.server.starsnap.adapter_infrastructure.report.entity.base.ReportBaseEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SnapEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.report.entity.SnapReport
import java.time.LocalDateTime

@Entity
@Table(name = "snap_report")
class SnapReportEntity(
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "snap_id", nullable = false, columnDefinition = "CHAR(16)")
    val snap: SnapEntity, // snap
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reporter_id", nullable = false, columnDefinition = "CHAR(16)")
    val reporter: UserEntity, // 신고자
    @Column(name = "explanation", nullable = false, updatable = false)
    val explanation: String, // 설명
    override var id: String,
    override var createdAt: LocalDateTime
) : ReportBaseEntity() {
    companion object {
        fun fromDomain(snapReport: SnapReport) = SnapReportEntity(
            snap = SnapEntity.fromDomain(snapReport.snap),
            reporter = UserEntity.fromDomain(snapReport.reporter),
            explanation = snapReport.explanation,
            id = snapReport.id,
            createdAt = snapReport.createdAt
        )
    }
}