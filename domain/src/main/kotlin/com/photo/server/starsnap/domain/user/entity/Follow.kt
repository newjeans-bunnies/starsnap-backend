package com.photo.server.starsnap.domain.user.entity

import com.photo.server.starsnap.domain.user.entity.base.FollowBase
import java.time.LocalDateTime

data class Follow(
    val followingUser: User,
    val followerUser: User,
    override val createdAt: LocalDateTime?,
    override val id: String
) : FollowBase(id, createdAt)