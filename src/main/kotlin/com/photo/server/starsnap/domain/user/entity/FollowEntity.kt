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
    @JoinColumn(name = "following_user_id", nullable = false, columnDefinition = "CHAR(16)")
    val followingUser: UserEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_user_id", nullable = false, columnDefinition = "CHAR(16)")
    val followerUser: UserEntity
) {
    // Follow 엔티티가 삭제 되면 자동으로 팔로워와 팔로우 숫자 -1
    @PostRemove
    fun removeFollowEntity() {
        followingUser.followingCount -= 1
        followerUser.followerCount -= 1
    }

    // Follow 엔티티가 생성 되면 자동으로 팔로워와 팔로우 숫자 +1
    @PrePersist
    fun createdFollowEntity() {
        followingUser.followingCount += 1
        followerUser.followerCount += 1
    }
}