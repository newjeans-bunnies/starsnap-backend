package com.photo.server.starsnap.domain.user.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "follow")
data class FollowEntity(
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "CHAR(16)")
    val id: String,
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_user_id", nullable = false, columnDefinition = "CHAR(16)")
    val followUser: UserEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "CHAR(16)")
    val user: UserEntity
)