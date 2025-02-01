package com.photo.server.starsnap.domain.user.service

import com.photo.server.starsnap.domain.user.controller.dto.BlackUserDto
import com.photo.server.starsnap.domain.user.entity.BlackUserEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.domain.user.error.exception.NotFoundUserIdException
import com.photo.server.starsnap.domain.user.repository.BlackUserRepository
import com.photo.server.starsnap.domain.user.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BlackUserService(
    private val blackUserRepository: BlackUserRepository,
    private val userRepository: UserRepository,
    private val followService: FollowService
) {
    fun blackUser(userData: UserEntity, blackUserId: String) {
        val blackUserData = userRepository.findByIdOrNull(blackUserId) ?: throw NotFoundUserIdException
        val blackUser = BlackUserEntity(
            blackUserEntity = blackUserData,
            userEntity = userData
        )
        // 차단 할때 언팔로우도 같이 진행
        followService.unFollow(userData.id, blackUserId)
        blackUserRepository.save(blackUser)
    }

    fun unBlackUser(userData: UserEntity, unBlackUserId: String) {
        val blackUser = userRepository.findByIdOrNull(unBlackUserId) ?: throw NotFoundUserIdException
        blackUserRepository.deleteByUserAndBlackUser(userData, blackUser)
    }

    fun getBlackUser(size: Int, page: Int, userData: UserEntity): Slice<BlackUserDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val blackUserList =
            blackUserRepository.findSliceBy(pageRequest, userData) ?: throw RuntimeException("존재 하지 않는 차단리스트")

        return blackUserList.map {
            BlackUserDto(it.blackUser.username, it.blackUser.profileImageUrl)
        }
    }
}