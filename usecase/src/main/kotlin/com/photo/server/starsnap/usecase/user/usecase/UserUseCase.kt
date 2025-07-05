package com.photo.server.starsnap.usecase.user.usecase

import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.user.dto.UserDto

interface UserUseCase {
    fun changeUsername(username: String, user: User): UserDto
    fun getUser(userId: String): UserDto
}