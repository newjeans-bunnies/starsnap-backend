package com.photo.server.starsnap.usecase.star.usecase

import com.photo.server.starsnap.domain.star.entity.Star
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.star.dto.*

interface StarUseCase {
    fun getStar(starId: String): Star

    fun createStar(starDto: CreateStarRequestDto): StarResponseDto

    fun updateStar(starDto: UpdateStarRequestDto): StarResponseDto

    fun existStar(type: String, name: String): ExistDto

    fun joinStarGroup(joinStarGroupDto: JoinStarGroupDto): StatusDto
}