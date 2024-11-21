package com.photo.server.starsnap.domain.star.service

import com.photo.server.starsnap.domain.star.dto.CreateStarGroupDto
import com.photo.server.starsnap.domain.star.dto.ExistDto
import com.photo.server.starsnap.domain.star.dto.UpdateStarGroupDto
import com.photo.server.starsnap.domain.star.dto.toEntity
import com.photo.server.starsnap.domain.star.repository.StarGroupRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StarGroupService(
    private val starGroupRepository: StarGroupRepository
) {
    fun createStarGroup(starGroupDto: CreateStarGroupDto) {
        val starGroup = starGroupDto.toEntity()
        starGroupRepository.save(starGroup)
    }

    fun updateStarGroup(starGroupDto: UpdateStarGroupDto) {
        val starGroup =
            starGroupRepository.findByIdOrNull(starGroupDto.id) ?: throw RuntimeException("존재 하지 않는 StarGroup")
        starGroup.name = starGroupDto.name
        starGroup.debutDate = starGroupDto.debutDate
        starGroupRepository.save(starGroup)
    }

    fun existStarGroup(type: String, name: String): ExistDto {
        val exist: Boolean = when (type) {
            "name" -> starGroupRepository.existsByName(name)
            else -> throw RuntimeException("존재하지 않는 타입")
        }

        return ExistDto(exist, type)
    }
}