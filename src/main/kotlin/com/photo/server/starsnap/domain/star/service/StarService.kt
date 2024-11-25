package com.photo.server.starsnap.domain.star.service

import com.photo.server.starsnap.domain.star.dto.ExistDto
import com.photo.server.starsnap.domain.star.dto.CreateStarDto
import com.photo.server.starsnap.domain.star.dto.UpdateStarDto
import com.photo.server.starsnap.domain.star.dto.toEntity
import com.photo.server.starsnap.domain.star.repository.StarRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StarService(
    private val starRepository: StarRepository
) {
    fun createStar(starDto: CreateStarDto) {
        starRepository.save(starDto.toEntity())
    }

    fun updateStar(starDto: UpdateStarDto) {
        val star = starRepository.findByIdOrNull(starDto.id) ?: throw RuntimeException("존재 하지 않는 Star")
        with(star) {
            name = starDto.name
            explanation = starDto.explanation
            nickname = starDto.nickname
            birthday = starDto.birthday
            gender = starDto.gender
        }
        starRepository.save(star)
    }

    fun existStar(type: String, name: String): ExistDto {
        val exist: Boolean = when (type) {
            "name" -> starRepository.existsByName(name)
            "nickname" -> starRepository.existsByNickname(name)
            else -> throw RuntimeException("존재하지 않는 타입")
        }
        return ExistDto(exist, type)
    }
}