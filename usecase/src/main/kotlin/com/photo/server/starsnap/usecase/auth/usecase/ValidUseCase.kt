package com.photo.server.starsnap.usecase.auth.usecase

import com.photo.server.starsnap.usecase.global.dto.StatusDto


interface ValidUseCase {
    fun validUsername(username: String): StatusDto

    fun validEmail(email: String): StatusDto
}