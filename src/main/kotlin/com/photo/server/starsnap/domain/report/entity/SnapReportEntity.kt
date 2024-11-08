package com.photo.server.starsnap.domain.report.entity

import com.photo.server.starsnap.domain.snap.entity.SnapEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "snap_report")
data class SnapReportEntity(
    @Id
    val id: String,
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    var createdAt: String, // 신고 시간
    @Column(name = "explanation", nullable = false, updatable = false)
    var explanation: String, // 설명
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "snap_id", nullable = false, columnDefinition = "CHAR(16)")
    val snap: SnapEntity, // snap
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reporter_id", nullable = false, columnDefinition = "CHAR(16)")
    val reporter: UserEntity // 신고자
)