package com.photo.server.starsnap.adapter_usecase.star.usecase

import com.photo.server.starsnap.adapter_infrastructure.star.repository.StarGroupRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.star.repository.StarRepositoryImpl
import com.photo.server.starsnap.exception.star.error.exception.NotFoundStarGroupIdException
import com.photo.server.starsnap.exception.star.error.exception.NotFoundStarIdException
import com.photo.server.starsnap.exception.star.error.exception.UnsupportedTypeValueException
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.star.dto.CreateStarRequestDto
import com.photo.server.starsnap.usecase.star.dto.ExistDto
import com.photo.server.starsnap.usecase.star.dto.JoinStarGroupDto
import com.photo.server.starsnap.usecase.star.dto.StarResponseDto
import com.photo.server.starsnap.usecase.star.dto.UpdateStarRequestDto
import com.photo.server.starsnap.usecase.star.dto.toDto
import com.photo.server.starsnap.usecase.star.dto.toEntity
import com.photo.server.starsnap.usecase.star.usecase.StarUseCase
import org.springframework.stereotype.Service

@Service
class StarUseCaseImpl(
    private val starRepositoryImpl: StarRepositoryImpl,
    private val starGroupRepositoryImpl: StarGroupRepositoryImpl
) : StarUseCase {

    // 스타 조회
    override fun getStar(starId: String) = starRepositoryImpl.findByIdOrNull(starId) ?: throw NotFoundStarIdException

    // 스타 생성
    override fun createStar(starDto: CreateStarRequestDto): StarResponseDto {
        val star = starRepositoryImpl.save(starDto.toEntity())
        return star.toDto()
    }

    // 스타 수정
    override fun updateStar(starDto: UpdateStarRequestDto): StarResponseDto {
        val star = starRepositoryImpl.findByIdOrNull(starDto.id) ?: throw NotFoundStarIdException
        with(star) {
            name = starDto.name
            explanation = starDto.explanation
            nickname = starDto.nickname
            birthday = starDto.birthday
            gender = starDto.gender
        }
        starRepositoryImpl.save(star)
        return star.toDto()
    }

    // 스타 유효성 확인
    override fun existStar(type: String, name: String): ExistDto {
        val exist: Boolean = when (type) {
            "name" -> starRepositoryImpl.existsByName(name)
            "nickname" -> starRepositoryImpl.existsByNickname(name)
            else -> throw UnsupportedTypeValueException
        }
        return ExistDto(exist, type)
    }

    // 스타 그룹에 스타 연결
    override fun joinStarGroup(joinStarGroupDto: JoinStarGroupDto): StatusDto {
        val star = starRepositoryImpl.findByIdOrNull(joinStarGroupDto.starId) ?: throw NotFoundStarIdException
        val starGroup =
            starGroupRepositoryImpl.findByIdOrNull(joinStarGroupDto.starGroupId)
                ?: throw NotFoundStarGroupIdException

        star.starGroup = starGroup
        starRepositoryImpl.save(star)

        return StatusDto("connection successful", 200)
    }
}