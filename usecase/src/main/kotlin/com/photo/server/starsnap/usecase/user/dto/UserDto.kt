package com.photo.server.starsnap.usecase.user.dto

import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.snap.dto.SnapUserDto


data class UserDto(
    val userId: String,
    val username: String,
    val email: String,
    val profileImageUrl: String? = null,
    val authority: String,
    val followingCount: Int,
    val followerCount: Int
)

fun User.toUserDto() = UserDto(
    userId = this.id,
    username = this.username,
    email = this.email,
    profileImageUrl = this.profileImageUrl,
    authority = this.authority.name,
    followingCount = this.followingCount,
    followerCount = this.followerCount
)

fun User.toSnapUserDto() = SnapUserDto(
    username = this.username,
    imageKey = this.profileImageUrl
)
