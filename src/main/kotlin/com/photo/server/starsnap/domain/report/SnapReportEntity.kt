package com.photo.server.starsnap.domain.report

import com.photo.server.starsnap.domain.snap.SnapEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "report")
data class SnapReportEntity(
    @Id
    val id: String,
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    var createdAt: String, // 신고 시간
    @Column(name = "explanation", nullable = false, updatable = false)
    var explanation: String, // 신고 시간
    @OneToOne
    @JoinColumn(name = "reporter", nullable = false, updatable = false)
    val reporter: UserEntity, // 신고 한 사람
    @OneToOne
    @JoinColumn(name = "snap_id", nullable = false, updatable = false)
    val snap: SnapEntity // snap
)
