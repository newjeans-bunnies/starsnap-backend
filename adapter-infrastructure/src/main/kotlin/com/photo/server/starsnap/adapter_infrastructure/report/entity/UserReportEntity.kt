package com.photo.server.starsnap.adapter_infrastructure.report.entity

import jakarta.persistence.*
import com.photo.server.starsnap.adapter_infrastructure.report.entity.base.ReportBaseEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.report.entity.UserReport

@Entity
@Table(name = "user_report")
class UserReportEntity(
    @Column(name = "explanation", nullable = false, updatable = false, columnDefinition = "VARCHAR(500)")
    val explanation: String, // 설명
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reporter_id", nullable = false, columnDefinition = "CHAR(16)")
    val reporter: UserEntity, // 신고자
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "defendant_id", nullable = false, columnDefinition = "CHAR(16)")
    val defendant: UserEntity // 피고인
) : ReportBaseEntity() {
    fun toDomain() = UserReport(
        explanation = this.explanation,
        reporter = this.reporter.toDomain(),
        defendant = this.defendant.toDomain(),
        createdAt = this.createdAt,
        id = this.id
    )

    companion object {
        fun fromDomain(userReport: UserReport) = UserReportEntity(
            explanation = userReport.explanation,
            reporter = UserEntity.fromDomain(userReport.reporter),
            defendant = UserEntity.fromDomain(userReport.defendant)
        )
    }
}