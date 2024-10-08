package com.photo.server.starsnap.domain.user.service

import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.global.dto.UserDto
import com.photo.server.starsnap.global.dto.toUserDto
import com.photo.server.starsnap.global.error.exception.ExistUsernameException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import com.photo.server.starsnap.global.error.exception.NotExistUserIdException

@Service
class UserService(
    private val userRepository: UserRepository
) {

    val log: Logger = LoggerFactory.getLogger(this.javaClass.getSimpleName())

    fun changeUsername(username: String, userId: String): UserDto {

        // 유저 찾기
        val userData = userRepository.findByIdOrNull(userId) ?: throw NotExistUserIdException

        // 같은 이름으로 못정하게
        if (userData.username == username) throw ExistUsernameException

        // 이미 존재 하는 닉네임일 경우 exception
        if (userRepository.existsByUsername(username)) throw ExistUsernameException

        // 유저 닉네임 변경
        userData.username = username
        userRepository.save(userData)

        return userData.toUserDto()
    }

    fun getUserData(username: String): UserDto {
        // 유저 찾기
        val userData = userRepository.findByUsername(username) ?: throw NotExistUserIdException
        return userData.toUserDto()
    }

}