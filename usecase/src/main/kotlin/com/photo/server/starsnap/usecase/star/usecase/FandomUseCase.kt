package com.photo.server.starsnap.usecase.star.usecase

import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.usecase.star.dto.CreateFandomRequestDto
import com.photo.server.starsnap.usecase.star.dto.FandomDto
import com.photo.server.starsnap.usecase.star.dto.UpdateFandomRequestDto

interface FandomUseCase {
    fun createFandom(fandomDto: CreateFandomRequestDto): FandomDto

    fun updateFandom(fandomDto: UpdateFandomRequestDto): FandomDto

    fun deleteFandom(fandomId: String)

    fun getFandoms(size: Int, page: Int): Slice<FandomDto>
}

