package com.photo.server.starsnap.usecase.star.usecase

import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.star.entity.StarGroup
import com.photo.server.starsnap.usecase.star.dto.CreateStarGroupRequestDto
import com.photo.server.starsnap.usecase.star.dto.ExistDto
import com.photo.server.starsnap.usecase.star.dto.StarGroupResponseDto
import com.photo.server.starsnap.usecase.star.dto.UpdateStarGroupRequestDto

interface StarGroupUseCase {
    fun getStarGroup(starGroupId: String): StarGroup

    fun createStarGroup(starGroupDto: CreateStarGroupRequestDto): StarGroupResponseDto

    fun updateStarGroup(starGroupDto: UpdateStarGroupRequestDto): StarGroupResponseDto

    fun existStarGroup(type: String, name: String): ExistDto

    fun getStarGroup(size: Int, page: Int): Slice<StarGroupResponseDto>

}