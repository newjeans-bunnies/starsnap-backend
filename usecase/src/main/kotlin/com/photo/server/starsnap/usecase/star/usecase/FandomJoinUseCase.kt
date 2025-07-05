package com.photo.server.starsnap.usecase.star.usecase

import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.global.dto.StatusDto


interface FandomJoinUseCase {
    fun joinFandom(user: User, fandomId: String): StatusDto

    fun disconnectFandom(user: User, fandomId: String): StatusDto
}