package com.photo.server.starsnap.adapter_usecase.star.usecase

import com.photo.server.starsnap.adapter_infrastructure.star.repository.FandomRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.star.repository.StarGroupRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.global.extension.toDomainPageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import com.photo.server.starsnap.domain.star.entity.Fandom
import com.photo.server.starsnap.exception.star.error.exception.NotFoundFandomException
import com.photo.server.starsnap.exception.star.error.exception.NotFoundFandomIdException
import com.photo.server.starsnap.exception.star.error.exception.NotFoundStarGroupIdException
import com.photo.server.starsnap.usecase.star.dto.CreateFandomRequestDto
import com.photo.server.starsnap.usecase.star.dto.FandomDto
import com.photo.server.starsnap.usecase.star.dto.UpdateFandomRequestDto
import com.photo.server.starsnap.usecase.star.usecase.FandomUseCase
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import io.viascom.nanoid.NanoId
import java.time.LocalDateTime

@Service
class FandomUseCaseImpl(
    private val fandomRepositoryImpl: FandomRepositoryImpl,
    private val starGroupRepositoryImpl: StarGroupRepositoryImpl,
): FandomUseCase {
    // 팬덤 생성
    override fun createFandom(fandomDto: CreateFandomRequestDto): FandomDto {
        val starGroup = starGroupRepositoryImpl.findByIdOrNull(fandomDto.starGroupId) ?: throw NotFoundStarGroupIdException
        val fandom = Fandom(
            name = fandomDto.name,
            explanation = fandomDto.explanation,
            starGroup = starGroup,
            id = NanoId.generate(16),
            createAt = LocalDateTime.now()
        )
        fandomRepositoryImpl.save(fandom)
        return FandomDto(fandomDto.name, fandomDto.explanation, starGroup.name)
    }

    // 팬덤 수정
    override fun updateFandom(fandomDto: UpdateFandomRequestDto): FandomDto {
        val fandom = fandomRepositoryImpl.findByIdOrNull(fandomDto.fandomId) ?: throw NotFoundFandomIdException
        val starGroup =
            starGroupRepositoryImpl.findByIdOrNull(fandom.starGroup.id) ?: throw NotFoundStarGroupIdException
        fandom.name = fandomDto.name
        fandom.explanation = fandomDto.explanation

        fandomRepositoryImpl.save(fandom)

        return FandomDto(fandomDto.name, fandomDto.explanation, starGroup.name)
    }

    // 팬덤 삭제
    override fun deleteFandom(fandomId: String) {
        fandomRepositoryImpl.deleteById(fandomId)
    }

    // 팬덤 조회
    override fun getFandoms(size: Int, page: Int): Slice<FandomDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val fandoms = fandomRepositoryImpl.findSliceBy(pageRequest.toDomainPageRequest()) ?: throw NotFoundFandomException
        return fandoms.map {
            val starGroup = starGroupRepositoryImpl.findByIdOrNull(it.id) ?: throw NotFoundStarGroupIdException
            FandomDto(it.name, it.explanation, starGroup.name)
        }
    }
}