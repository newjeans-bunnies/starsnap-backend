package com.photo.server.starsnap.domain.report

import com.photo.server.starsnap.domain.user.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_report")
data class UserReportEntity(
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
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    val user: UserEntity // snap
)
