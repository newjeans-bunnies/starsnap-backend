package com.photo.server.starsnap.global.dto

import com.photo.server.starsnap.domain.user.entity.UserEntity

data class UserDto(
    val userId: String,
    val username: String,
    val email: String,
    val profileImageUrl: String? = null,
    val authority: String,
    val followCount: Int,
    val followerCount: Int
)

fun UserEntity.toUserDto() = UserDto(
    userId = id,
    username = username,
    email = email,
    profileImageUrl = profileImageUrl,
    authority = authority.name,
    followerCount = followerCount,
    followCount = followingCount
)