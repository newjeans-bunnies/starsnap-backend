package com.photo.server.starsnap.domain.user.controller.dto

import com.photo.server.starsnap.domain.user.entity.FollowEntity

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

fun FollowEntity.toFollowDto() = FollowingDto(
    id = id,
    username = followingUser.username,
    profileImageUrl = followingUser.profileImageUrl
)

fun FollowEntity.toFollowerDto() = FollowerDto(
    id = id,
    username = followerUser.username,
    profileImageUrl = followerUser.profileImageUrl
)