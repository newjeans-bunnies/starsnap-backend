package com.photo.server.starsnap.domain.user.service

import com.photo.server.starsnap.domain.user.repository.FollowRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FollowService(
    private val followRepository: FollowRepository
) {

    val logging = LoggerFactory.getLogger(this.javaClass)

    fun follow(userId: String, followUserId: String) {
        val userData = followRepository.findByIdOrNull(userId) ?: throw RuntimeException("존재 하지 않는 userId")
        val followUserData = followRepository.findByIdOrNull(followUserId) ?: throw RuntimeException("존재 하지 않는 userId")

        userData.follow += followUserData
        followRepository.save(userData)
    }

    fun unFollow(userId: String, unFollowUserId: String) {
        val userData = followRepository.findByIdOrNull(userId) ?: throw RuntimeException("존재 하지 않는 userId")
        val followUserData = followRepository.findByIdOrNull(unFollowUserId) ?: throw RuntimeException("존재 하지 않는 userId")

        userData.follow -= followUserData
        followRepository.save(userData)
    }
}