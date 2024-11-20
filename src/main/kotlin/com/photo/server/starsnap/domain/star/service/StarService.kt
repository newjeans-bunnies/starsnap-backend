package com.photo.server.starsnap.domain.star.service

import com.photo.server.starsnap.domain.star.dto.ExistDto
import com.photo.server.starsnap.domain.star.dto.CreateStarDto
import com.photo.server.starsnap.domain.star.dto.FixStarDto
import com.photo.server.starsnap.domain.star.dto.toEntity
import com.photo.server.starsnap.domain.star.repository.StarRepository
import org.springframework.stereotype.Service

@Service
class StarService(
    private val starRepository: StarRepository
) {
    fun createStar(starDto: CreateStarDto) {
        starRepository.save(starDto.toEntity())
    }

    fun fixStar(starDto: FixStarDto) {
        if(!starRepository.existsById(starDto.id)) throw RuntimeException("존재 하지 않는 Star")

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