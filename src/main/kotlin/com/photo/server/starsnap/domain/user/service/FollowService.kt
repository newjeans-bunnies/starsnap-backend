package com.photo.server.starsnap.domain.user.service

import com.photo.server.starsnap.domain.user.controller.dto.FollowerDto
import com.photo.server.starsnap.domain.user.controller.dto.FollowingDto
import com.photo.server.starsnap.domain.user.controller.dto.toFollowDto
import com.photo.server.starsnap.domain.user.controller.dto.toFollowerDto
import com.photo.server.starsnap.domain.user.entity.FollowEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.domain.user.repository.FollowRepository
import com.photo.server.starsnap.domain.user.repository.UserRepository
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

        val followData = followRepository.findByIdOrNull(followId) ?: throw RuntimeException("Follow ID $followId not found")
        if(followData.followerUser.id != userId) throw RuntimeException("권한 없음")

        followRepository.delete(followData)
    }

    fun getFollowing(userId: String, page: Int, size: Int): Slice<FollowingDto> {
        if (userRepository.existsById(userId)) throw RuntimeException("없는 userId")

        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )

        val followData = followRepository.getFollowing(pageRequest, userId) ?: throw RuntimeException("")

        return followData.map {
            it.toFollowDto()
        }
    }

    fun getFollowers(userId: String, page: Int, size: Int): Slice<FollowerDto> {
        if (userRepository.existsById(userId)) throw RuntimeException("없는 userId")

        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )

        val followData = followRepository.getFollowers(pageRequest, userId) ?: throw RuntimeException("")

        return followData.map {
            it.toFollowerDto()
        }

    }



    private fun getUsers(userId: String, followUserId: String): Pair<UserEntity, UserEntity> {
        val user = userRepository.findByIdOrNull(userId) ?: throw RuntimeException("없는 userId")
        val followUser = userRepository.findByIdOrNull(followUserId) ?: throw RuntimeException("없는 followUserId")
        return Pair(user, followUser)
    }
}