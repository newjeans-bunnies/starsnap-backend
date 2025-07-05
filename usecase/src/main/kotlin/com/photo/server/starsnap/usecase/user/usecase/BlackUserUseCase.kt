package com.photo.server.starsnap.usecase.user.usecase

import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.user.dto.BlackUserDto

interface BlackUserUseCase {

    fun blackUser(user: User, blackUserId: String)

    fun unBlackUser(user: User, unBlackUserId: String)

    fun getBlackUser(size: Int, page: Int, user: User): Slice<BlackUserDto>
}