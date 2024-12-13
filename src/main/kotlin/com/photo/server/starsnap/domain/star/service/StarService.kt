package com.photo.server.starsnap.domain.star.service

import com.photo.server.starsnap.domain.star.dto.*
import com.photo.server.starsnap.domain.star.error.exception.NotFoundStarGroupIdException
import com.photo.server.starsnap.domain.star.error.exception.NotFoundStarIdException
import com.photo.server.starsnap.domain.star.error.exception.UnsupportedTypeValueException
import com.photo.server.starsnap.domain.star.repository.StarGroupRepository
import com.photo.server.starsnap.domain.star.repository.StarRepository
import com.photo.server.starsnap.global.dto.StatusDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StarService(
    private val starRepository: StarRepository,
    private val starGroupRepository: StarGroupRepository,
) {
    fun createStar(starDto: CreateStarRequestDto): StarResponseDto {
        val star = starRepository.save(starDto.toEntity())
        return star.toDto()
    }

    fun updateStar(starDto: UpdateStarRequestDto): StarResponseDto {
        val star = starRepository.findByIdOrNull(starDto.id) ?: throw NotFoundStarIdException
        with(star) {
            name = starDto.name
            explanation = starDto.explanation
            nickname = starDto.nickname
            birthday = starDto.birthday
            gender = starDto.gender
        }
        starRepository.save(star)
        return star.toDto()
    }

    fun existStar(type: String, name: String): ExistDto {
        val exist: Boolean = when (type) {
            "name" -> starRepository.existsByName(name)
            "nickname" -> starRepository.existsByNickname(name)
            else -> throw UnsupportedTypeValueException
        }
        return ExistDto(exist, type)
    }

    fun joinStarGroup(joinStarGroupDto: JoinStarGroupDto): StatusDto {
        val star = starRepository.findByIdOrNull(joinStarGroupDto.starId) ?: throw NotFoundStarIdException
        val starGroup =
            starGroupRepository.findByIdOrNull(joinStarGroupDto.starGroupId)
                ?: throw NotFoundStarGroupIdException

        star.starGroupId = starGroup
        starRepository.save(star)

        return StatusDto("connection successful", 200)
    }
}