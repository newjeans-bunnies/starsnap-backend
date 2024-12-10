package com.photo.server.starsnap.domain.user.service

import com.photo.server.starsnap.domain.user.controller.dto.FollowerDto
import com.photo.server.starsnap.domain.user.controller.dto.FollowingDto
import com.photo.server.starsnap.domain.user.controller.dto.toFollowDto
import com.photo.server.starsnap.domain.user.controller.dto.toFollowerDto
import com.photo.server.starsnap.domain.user.entity.FollowEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.domain.user.error.exception.NotFoundFollowIdException
import com.photo.server.starsnap.domain.user.error.exception.NotFoundUserIdException
import com.photo.server.starsnap.domain.user.repository.FollowRepository
import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.global.error.exception.InvalidRoleException
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FollowService(
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository
) {

    private val logging = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun follow(userId: String, followUserId: String) {
        val (user, followUser) = getUsers(userId, followUserId)

        val followData = FollowEntity(
            followerUser = user,
            followingUser = followUser
        )

        followRepository.save(followData)
    }

    @Transactional
    fun unFollow(userId: String, followId: String) {

        val followData = followRepository.findByIdOrNull(followId) ?: throw NotFoundFollowIdException
        if(followData.followerUser.id != userId) throw InvalidRoleException

        followRepository.delete(followData)
    }

    fun getFollowing(userId: String, page: Int, size: Int): Slice<FollowingDto> {
        if (userRepository.existsById(userId)) throw NotFoundUserIdException

        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )

        val followData = followRepository.getFollowing(pageRequest, userId) ?: throw NotFoundUserIdException

        return followData.map {
            it.toFollowDto()
        }
    }

    fun getFollowers(userId: String, page: Int, size: Int): Slice<FollowerDto> {
        if (userRepository.existsById(userId)) throw NotFoundUserIdException

        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )

        val followData = followRepository.getFollowers(pageRequest, userId) ?: throw NotFoundUserIdException

        return followData.map {
            it.toFollowerDto()
        }

    }



    private fun getUsers(userId: String, followUserId: String): Pair<UserEntity, UserEntity> {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundUserIdException
        val followUser = userRepository.findByIdOrNull(followUserId) ?: throw NotFoundUserIdException
        return Pair(user, followUser)
    }
}