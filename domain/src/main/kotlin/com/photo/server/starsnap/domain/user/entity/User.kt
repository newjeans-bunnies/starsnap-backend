package com.photo.server.starsnap.domain.user.entity

import com.photo.server.starsnap.domain.user.entity.base.UserBase
import com.photo.server.starsnap.domain.user.type.Authority
import java.time.LocalDateTime

data class User(
    val authority: Authority,
    var username: String,
    var password: String? = null,
    val email: String,
    val profileImageUrl: String? = null,
    val followerCount: Int = 0,
    val followingCount: Int = 0,
    val saveCount: Int = 0,
    var state: Boolean = true,
    override val createdAt: LocalDateTime?,
    override val id: String,
    override var modifiedAt: LocalDateTime?
) : UserBase(id, createdAt, modifiedAt)