package com.photo.server.starsnap.domain.report.entity

import com.photo.server.starsnap.domain.auth.type.Authority
import jakarta.persistence.*

@Entity
@Table(name = "user_report")
data class UserReportEntity(
    @Id
    @Column(name = "id", columnDefinition = "CHAR(16)", nullable = false)
    val id: String,
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    var createdAt: String, // 신고 시간
    @Column(name = "explanation", nullable = false, updatable = false, columnDefinition = "VARCHAR(500)")
    var explanation: String, // 설명
    @Column(name = "reporter_id", nullable = false, updatable = false)
    val reporterId: String,
    @Column(name = "reporter_username", nullable = false, updatable = false)
    val reporterUsername: String,
    @Column(name = "reporter_email", nullable = false, updatable = false)
    val reporterEmail: String,
    @Column(name = "reporter_authority", nullable = false, updatable = false)
    val reporterAuthority: String,
    @Column(name = "defendant_id", nullable = false, updatable = false)
    val defendantId: String,
    @Column(name = "defendant_username", nullable = false, updatable = false)
    val defendantUsername: String,
    @Column(name = "defendant_email", nullable = false, updatable = false)
    val defendantEmail: String,
    @Enumerated(value = EnumType.STRING)
    @Column(name = "defendant_authority", nullable = false, updatable = false)
    val defendantAuthority: Authority
)
