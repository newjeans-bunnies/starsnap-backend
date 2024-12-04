package com.photo.server.starsnap.domain.star.service

import com.photo.server.starsnap.domain.star.dto.CreateFandomRequestDto
import com.photo.server.starsnap.domain.star.dto.FandomDto
import com.photo.server.starsnap.domain.star.dto.UpdateFandomRequestDto
import com.photo.server.starsnap.domain.star.repository.FandomRepository
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service

@Service
class FandomService(
    private val fandomRepository: FandomRepository
) {
    fun createFandom(fandomDto: CreateFandomRequestDto): FandomDto {}
    fun updateFandom(fandomDto: UpdateFandomRequestDto): FandomDto {}
    fun deleteFandom(fandomId: String) {}
    fun getFandoms(size: Int, page: Int): Slice<FandomDto> {}

    fun joinFandom(userId: String, fandomId: String) {}
    fun disconnectFandom(userId: String, fandomId: String) {}
}