package com.photo.server.starsnap.domain.user.service

import com.photo.server.starsnap.domain.user.entity.FollowEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.domain.user.repository.FollowRepository
import com.photo.server.starsnap.domain.user.repository.UserRepository
import io.viascom.nanoid.NanoId
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

    val logging = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun follow(userId: String, followUserId: String) {
        val (user, followUser) = getUsers(userId, followUserId)

        val followData = FollowEntity(
            id = NanoId.generate(16),
            user = user,
            followUser = followUser
        )

        user.follow += 1
        followUser.follower += 1

        userRepository.save(user)
        userRepository.save(followUser)
        followRepository.save(followData)
    }

        followRepository.save(userData)
        followRepository.save(followUserData)

    }

    fun unFollow(userId: String, unFollowUserId: String) {
        val (user, unFollowUser) = getUsers(userId, unFollowUserId)

        val followData = followRepository.findByFollowUserAndUser(unFollowUser, user)

        user.follow -= 1
        unFollowUser.follower -= 1

        userRepository.save(user)
        userRepository.save(unFollowUser)
        followRepository.delete(followData)
    }

    private fun getUsers(userId: String, followUserId: String): Pair<UserEntity, UserEntity> {
        val user = userRepository.findByIdOrNull(userId) ?: throw RuntimeException("없는 userId")
        val followUser = userRepository.findByIdOrNull(followUserId) ?: throw RuntimeException("없는 followUserId")
        return Pair(user, followUser)
    }
}