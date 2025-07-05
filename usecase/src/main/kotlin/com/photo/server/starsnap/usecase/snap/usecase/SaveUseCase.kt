package com.photo.server.starsnap.usecase.snap.usecase

import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.global.dto.StatusDto

interface SaveUseCase {
    fun saveSnap(user: User, snapId: String): StatusDto

    fun unSaveSnap(userId: String, snapId: String): StatusDto
}