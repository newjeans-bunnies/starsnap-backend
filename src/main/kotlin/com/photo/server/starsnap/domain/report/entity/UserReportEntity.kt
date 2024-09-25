package com.photo.server.starsnap.domain.report.entity

import com.photo.server.starsnap.domain.user.entity.UserEntity
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reporter_id", nullable = false, columnDefinition = "CHAR(16)")
    val reporter: UserEntity, // 신고자
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "defendant_id", nullable = false, columnDefinition = "CHAR(16)")
    val defendant: UserEntity // 피고인
)