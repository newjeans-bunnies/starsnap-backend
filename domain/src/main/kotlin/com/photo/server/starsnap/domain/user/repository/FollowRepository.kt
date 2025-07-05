package com.photo.server.starsnap.domain.user.repository

import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.domain.user.entity.Follow

interface FollowRepository {
    fun getFollowing(pageable: PageRequest, user: User): Slice<Follow>?
    fun getFollowers(pageable: PageRequest, user: User): Slice<Follow>?
    fun save(follow: Follow): Follow
    fun delete(follow: Follow)
    fun findByIdOrNull(id: String): Follow?
    fun findByFollowUserAndFollowerUserId(followUser: User, followerUserId: String): Follow?
}