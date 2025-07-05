package com.photo.server.starsnap.usecase.snap.usecase

import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.global.dto.StatusDto

interface LikeUseCase {
    fun snapLikeSnap(user: User, snapId: String): StatusDto

    fun snapUnlikeSnap(user: User, snapId: String): StatusDto

    fun commentLikeSnap(user: User, commentId: String): StatusDto

    fun commentUnlikeSnap(user: User, commentId: String): StatusDto
}