package com.photo.server.starsnap.domain.star.service

import com.photo.server.starsnap.domain.star.dto.*
import com.photo.server.starsnap.domain.star.error.exception.NotFoundStarGroupIdException
import com.photo.server.starsnap.domain.star.error.exception.UnsupportedTypeValueException
import com.photo.server.starsnap.domain.star.repository.StarGroupRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StarGroupService(
    private val starGroupRepository: StarGroupRepository
) {
    fun createStarGroup(starGroupDto: CreateStarGroupRequestDto): StarGroupResponseDto {
        val starGroup = starGroupDto.toEntity()
        starGroupRepository.save(starGroup)

        return starGroup.toDto()
    }

    fun updateStarGroup(starGroupDto: UpdateStarGroupRequestDto): StarGroupResponseDto {
        val starGroupId = starGroupDto.id
        val starGroup =
            starGroupRepository.findByIdOrNull(starGroupId) ?: throw NotFoundStarGroupIdException
        with(starGroup) {
            name = starGroupDto.name
            debutDate = starGroupDto.debutDate
            explanation = starGroupDto.explanation
        }
        starGroupRepository.save(starGroup)

        return starGroup.toDto()
    }

    fun existStarGroup(type: String, name: String): ExistDto {
        val exist: Boolean = when (type) {
            "name" -> starGroupRepository.existsByName(name)
            else -> throw UnsupportedTypeValueException
        }

        return ExistDto(exist, type)
    }

    fun getStarGroup(size: Int, page: Int): Slice<StarGroupResponseDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )

        val starGroups = starGroupRepository.findSliceBy(pageRequest) ?: throw NotFoundStarGroupIdException
        return starGroups.map { it.toDto() }
    }
}