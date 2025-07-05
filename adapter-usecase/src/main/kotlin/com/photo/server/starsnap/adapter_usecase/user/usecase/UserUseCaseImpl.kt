package com.photo.server.starsnap.adapter_usecase.user.usecase

import com.photo.server.starsnap.adapter_infrastructure.user.repository.UserRepositoryImpl
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.exception.global.error.exception.ExistUsernameException
import com.photo.server.starsnap.exception.global.error.exception.NotExistUserIdException
import com.photo.server.starsnap.usecase.user.dto.UserDto
import com.photo.server.starsnap.usecase.user.dto.toUserDto
import com.photo.server.starsnap.usecase.user.usecase.UserUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserUseCaseImpl(
    private val userRepositoryImpl: UserRepositoryImpl,
) : UserUseCase {
    val log: Logger = LoggerFactory.getLogger(this.javaClass.getSimpleName())

    override fun changeUsername(username: String, user: User): UserDto {

        // 같은 이름으로 못정하게
        if (user.username == username) throw ExistUsernameException

        // 이미 존재 하는 닉네임일 경우 exception
        if (userRepositoryImpl.existsByUsername(username)) throw ExistUsernameException

        // 유저 닉네임 변경
        user.username = username
        userRepositoryImpl.save(user)

        return user.toUserDto()
    }

    override fun getUser(userId: String): UserDto {
        // 유저 찾기
        val userData = userRepositoryImpl.findByIdOrNull(userId) ?: throw NotExistUserIdException
        return userData.toUserDto()
    }
}