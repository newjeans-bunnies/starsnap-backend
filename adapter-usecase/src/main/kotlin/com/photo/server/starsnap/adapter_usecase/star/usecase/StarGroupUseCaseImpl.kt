package com.photo.server.starsnap.adapter_usecase.star.usecase

import com.photo.server.starsnap.adapter_infrastructure.star.repository.StarGroupRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.extension.toDomainPageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import com.photo.server.starsnap.exception.star.error.exception.NotFoundStarGroupIdException
import com.photo.server.starsnap.exception.star.error.exception.UnsupportedTypeValueException
import com.photo.server.starsnap.usecase.star.dto.CreateStarGroupRequestDto
import com.photo.server.starsnap.usecase.star.dto.ExistDto
import com.photo.server.starsnap.usecase.star.dto.StarGroupResponseDto
import com.photo.server.starsnap.usecase.star.dto.UpdateStarGroupRequestDto
import com.photo.server.starsnap.usecase.star.dto.toDto
import com.photo.server.starsnap.usecase.star.dto.toEntity
import com.photo.server.starsnap.usecase.star.usecase.StarGroupUseCase
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class StarGroupUseCaseImpl(
    private val starGroupRepositoryImpl: StarGroupRepositoryImpl
): StarGroupUseCase {

    // 스타그룹 조회
    override fun getStarGroup(starGroupId: String) = starGroupRepositoryImpl.findByIdOrNull(starGroupId) ?: throw NotFoundStarGroupIdException

    // 스타그룹 생성
    override fun createStarGroup(starGroupDto: CreateStarGroupRequestDto): StarGroupResponseDto {
        val starGroup = starGroupDto.toEntity()
        starGroupRepositoryImpl.save(starGroup)

        return starGroup.toDto()
    }

    // 스타그룹 수정
    override fun updateStarGroup(starGroupDto: UpdateStarGroupRequestDto): StarGroupResponseDto {
        val starGroupId = starGroupDto.id
        val starGroup =
            starGroupRepositoryImpl.findByIdOrNull(starGroupId) ?: throw NotFoundStarGroupIdException
        with(starGroup) {
            name = starGroupDto.name
            debutDate = starGroupDto.debutDate
            explanation = starGroupDto.explanation
        }
        starGroupRepositoryImpl.save(starGroup)

        return starGroup.toDto()
    }

    // 스타그룹 유효성 확인
    override fun existStarGroup(type: String, name: String): ExistDto {
        val exist: Boolean = when (type) {
            "name" -> starGroupRepositoryImpl.existsByName(name)
            else -> throw UnsupportedTypeValueException
        }

        return ExistDto(exist, type)
    }

    // 스타그룹 가져오기
    override fun getStarGroup(size: Int, page: Int): Slice<StarGroupResponseDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )

        val starGroups = starGroupRepositoryImpl.findSliceBy(pageRequest.toDomainPageRequest()) ?: throw NotFoundStarGroupIdException
        return starGroups.map { it.toDto() }
    }
}