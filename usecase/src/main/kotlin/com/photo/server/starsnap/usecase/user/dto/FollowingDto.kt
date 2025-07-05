package com.photo.server.starsnap.usecase.user.dto

import com.photo.server.starsnap.domain.user.entity.Follow

data class FollowingDto(
    val id: String,
    val username: String,
    val profileImageUrl: String?
)

data class FollowerDto(
    val id: String,
    val username: String,
    val profileImageUrl: String?
)

fun Follow.toFollowDto() = FollowingDto(
    id = id,
    username = followingUser.username,
    profileImageUrl = followingUser.profileImageUrl
)

fun Follow.toFollowerDto() = FollowerDto(
    id = id,
    username = followerUser.username,
    profileImageUrl = followerUser.profileImageUrl
)