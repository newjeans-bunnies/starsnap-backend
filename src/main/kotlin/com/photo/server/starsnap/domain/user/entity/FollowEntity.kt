package com.photo.server.starsnap.domain.user.entity

import com.photo.server.starsnap.domain.user.entity.base.FollowBaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "follow")
class FollowEntity(
    followingUser: UserEntity,
    followerUser: UserEntity
) : FollowBaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_user_id", nullable = false, columnDefinition = "CHAR(16)")
    var followingUser: UserEntity = followingUser
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_user_id", nullable = false, columnDefinition = "CHAR(16)")
    var followerUser: UserEntity = followerUser
        protected set


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
}