package com.photo.server.starsnap.adapter_infrastructure.user.entity

import com.photo.server.starsnap.domain.user.entity.Follow
import jakarta.persistence.*
import com.photo.server.starsnap.adapter_infrastructure.user.entity.base.FollowBaseEntity

@Entity
@Table(name = "follow")
class FollowEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_user_id", nullable = false, columnDefinition = "CHAR(16)")
    val followingUser: UserEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_user_id", nullable = false, columnDefinition = "CHAR(16)")
    val followerUser: UserEntity
) : FollowBaseEntity() {

    // Follow 엔티티가 삭제 되면 자동으로 팔로워와 팔로우 숫자 -1
    @PostRemove
    fun removeFollow() {
        followingUser.followingCount -= 1
        followerUser.followerCount -= 1
    }

    // Follow 엔티티가 생성 되면 자동으로 팔로워와 팔로우 숫자 +1
    @PrePersist
    fun createdFollow() {
        followingUser.followingCount += 1
        followerUser.followerCount += 1
    }

    companion object {
        fun fromDomain(follow: Follow) = FollowEntity(
            followingUser = UserEntity.fromDomain(follow.followerUser),
            followerUser = UserEntity.fromDomain(follow.followerUser)
        )
    }
}