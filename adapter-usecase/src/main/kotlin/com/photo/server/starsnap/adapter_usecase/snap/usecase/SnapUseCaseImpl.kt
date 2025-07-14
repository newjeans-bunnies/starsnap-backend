package com.photo.server.starsnap.adapter_usecase.snap.usecase

import com.photo.server.starsnap.adapter_infrastructure.extension.toDomainPageRequest
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.SnapRepositoryImpl
import com.photo.server.starsnap.adapter_usecase.star.usecase.StarGroupUseCaseImpl
import com.photo.server.starsnap.adapter_usecase.star.usecase.StarUseCaseImpl
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import com.photo.server.starsnap.domain.snap.entity.Snap
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.exception.global.error.exception.InvalidRoleException
import com.photo.server.starsnap.exception.snap.error.exception.NotFoundSnapException
import com.photo.server.starsnap.exception.snap.error.exception.NotFoundSnapIdException
import com.photo.server.starsnap.usecase.snap.dto.CreateSnapRequestDto
import com.photo.server.starsnap.usecase.snap.dto.CreateSnapResponseDto
import com.photo.server.starsnap.usecase.snap.dto.GetSnapResponseDto
import com.photo.server.starsnap.usecase.snap.dto.SnapResponseDto
import com.photo.server.starsnap.usecase.snap.dto.UpdateSnapRequestDto
import com.photo.server.starsnap.usecase.snap.dto.toCreateSnapResponseDto
import com.photo.server.starsnap.usecase.snap.dto.toSnapDto
import com.photo.server.starsnap.usecase.snap.usecase.SnapUseCase
import com.photo.server.starsnap.usecase.user.dto.toSnapUserDto
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import io.viascom.nanoid.NanoId
import java.time.LocalDateTime

@Service
class SnapUseCaseImpl(
    private val snapRepositoryImpl: SnapRepositoryImpl,
    private val tagUseCaseImpl: TagUseCaseImpl,
    private val starCaseImpl: StarUseCaseImpl,
    private val starGroupCaseImpl: StarGroupUseCaseImpl,
): SnapUseCase {
    // 스냅 생성
    override fun createSnap(
        userData: User,
        snapDto: CreateSnapRequestDto
    ): CreateSnapResponseDto {
        val stars = snapDto.starIds.map { id ->
            starCaseImpl.getStar(id)
        }

        val starGroups = snapDto.starGroupIds.map { id ->
            starGroupCaseImpl.getStarGroup(id)
        }

        val snapData = Snap(
            id = NanoId.generate(16),
            title = snapDto.title,
            user = userData,
            state = true,
            likeCount = 0,
            description = snapDto.description,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )

        val temp = snapRepositoryImpl.save(snapData)

        return temp.toCreateSnapResponseDto()
    }

    // 스냅 삭제
    override fun deleteSnap(user: User, snapId: String?) {
        val snap = snapRepositoryImpl.findByIdOrNull(snapId) ?: throw NotFoundSnapIdException

        if (snap.user.id != user.id) throw InvalidRoleException

        snap.state = false
        snapRepositoryImpl.save(snap)
    }

    // 스냅 수정
    override fun updateSnap(
        snapDto: UpdateSnapRequestDto,
        user: User
    ): SnapResponseDto {
        val snapData = snapRepositoryImpl.findByIdOrNull(snapDto.snapId) ?: throw NotFoundSnapIdException

        val stars = snapDto.starIds.map { id ->
            starCaseImpl.getStar(id)
        }

        val starGroups = snapDto.starGroupIds.map { id ->
            starGroupCaseImpl.getStarGroup(id)
        }

        if (snapData.user.id != user.id) throw InvalidRoleException

        with(snapData) {
            snapData.title = snapDto.title
            snapData.starGroups = starGroups
            snapData.stars = stars
        }

        snapRepositoryImpl.save(snapData)

        return SnapResponseDto(
            createdUser = snapData.user.toSnapUserDto(),
            snapData = snapData.toSnapDto()
        )
    }

    // 스냅 상세 조회
    override fun getSnap(getSnapResponseDto: GetSnapResponseDto, user: User): Slice<SnapResponseDto> {
        val pageRequest = PageRequest.of(
            getSnapResponseDto.page, getSnapResponseDto.size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val snapData = snapRepositoryImpl.findFilteredSnaps(
            pageable = pageRequest.toDomainPageRequest(),
            state = true,
            blockUser = null,
            tags = getSnapResponseDto.tag,
            title = getSnapResponseDto.title,
            star = getSnapResponseDto.starId,
            starGroup = getSnapResponseDto.starGroupId,
            userId = user.id
        ) ?: throw NotFoundSnapException

        return snapData.map {
            SnapResponseDto(
                createdUser = it.user.toSnapUserDto(),
                snapData = it.toSnapDto()
            )
        }
    }

    // 스냅 전체 조회
    override fun getAllSnap(): List<SnapResponseDto> {
        val snap = snapRepositoryImpl.findAll() ?: throw NotFoundSnapException

        return snap.map {
            SnapResponseDto(
                createdUser = it.user.toSnapUserDto(),
                snapData = it.toSnapDto()
            )
        }
    }
}