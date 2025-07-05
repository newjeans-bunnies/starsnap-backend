package com.photo.server.starsnap.usecase.user.usecase

import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.user.dto.FollowerDto
import com.photo.server.starsnap.usecase.user.dto.FollowingDto

interface FollowUseCase {

    fun follow(user: User, followUserId: String)

    fun unFollow(user: User, followUserId: String)

    fun getFollowing(user: User, page: Int, size: Int): Slice<FollowingDto>

    fun getFollower(user: User, page: Int, size: Int): Slice<FollowerDto>
}