package com.photo.server.starsnap.domain.star.service

import com.photo.server.starsnap.domain.star.dto.CreateFandomRequestDto
import com.photo.server.starsnap.domain.star.dto.FandomDto
import com.photo.server.starsnap.domain.star.dto.UpdateFandomRequestDto
import com.photo.server.starsnap.domain.star.entity.FandomEntity
import com.photo.server.starsnap.domain.star.repository.FandomRepository
import com.photo.server.starsnap.domain.star.repository.StarGroupRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FandomService(
    private val fandomRepository: FandomRepository,
    private val starGroupRepository: StarGroupRepository,
) {
    fun createFandom(fandomDto: CreateFandomRequestDto): FandomDto {
        val starGroup =
            starGroupRepository.findByIdOrNull(fandomDto.starGroupId) ?: throw RuntimeException("존재 하지 않는 starGroupId")
        val fandom = FandomEntity(fandomDto.name, fandomDto.explanation, starGroup)
        fandomRepository.save(fandom)
        return FandomDto(fandomDto.name, fandomDto.explanation, starGroup.name)
    }

    fun updateFandom(fandomDto: UpdateFandomRequestDto): FandomDto {
        val fandom = fandomRepository.findByIdOrNull(fandomDto.fandomId) ?: throw RuntimeException("존재 하지 않는 fandomId")
        val starGroup =
            starGroupRepository.findByIdOrNull(fandom.starGroupId.id) ?: throw RuntimeException("존재 하지 않는 starGroupId")
        fandom.name = fandomDto.name
        fandom.explanation = fandomDto.explanation

        fandomRepository.save(fandom)

        return FandomDto(fandomDto.name, fandomDto.explanation, starGroup.name)
    }

    fun deleteFandom(fandomId: String) {
        fandomRepository.deleteById(fandomId)
    }

    fun getFandoms(size: Int, page: Int): Slice<FandomDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val fandoms = fandomRepository.findSliceBy(pageRequest) ?: throw RuntimeException("fandom이 null일수 없음")
        return fandoms.map {
            val starGroup = starGroupRepository.findByIdOrNull(it.id) ?: throw RuntimeException("존재 하지 않는 starGroupId")
            FandomDto(it.name, it.explanation, starGroup.name)
        }
    }
}