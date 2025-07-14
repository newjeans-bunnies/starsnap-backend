package com.photo.server.starsnap.adapter_infrastructure.user.repository

import com.photo.server.starsnap.adapter_infrastructure.extension.toCommonSlice
import com.photo.server.starsnap.adapter_infrastructure.extension.toSpringPageable
import com.photo.server.starsnap.adapter_infrastructure.user.UserMapper.toFollow
import com.photo.server.starsnap.adapter_infrastructure.user.entity.FollowEntity
import com.photo.server.starsnap.domain.user.entity.Follow
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.user.repository.springdata.FollowCrudRepository
import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.domain.user.repository.FollowRepository
import org.springframework.data.repository.findByIdOrNull

@Repository
class FollowRepositoryImpl(
    private val followCrudRepository: FollowCrudRepository
): FollowRepository {
    override fun getFollowing(pageable: PageRequest, user: User): Slice<Follow>? {
        return followCrudRepository.getFollowing(pageable.toSpringPageable(), user)?.toCommonSlice()?.map { it.toFollow() }
    }

    override fun getFollowers(pageable: PageRequest, user: User): Slice<Follow>? {
        return followCrudRepository.getFollowers(pageable.toSpringPageable(), user)?.toCommonSlice()?.map { it.toFollow() }
    }

    override fun save(follow: Follow): Follow {
        return followCrudRepository.save(FollowEntity.fromDomain(follow)).toFollow()
    }

    override fun delete(follow: Follow) {
        followCrudRepository.delete(FollowEntity.fromDomain(follow))
    }

    override fun findByIdOrNull(id: String): Follow? {
        return followCrudRepository.findByIdOrNull(id)?.toFollow()
    }

    override fun findByFollowUserAndFollowerUserId(followUser: User, followerUserId: String): Follow? {
        return followCrudRepository.findByFollowUserIdAndFollowerUserId(followUser, followerUserId)?.toFollow()
    }
}