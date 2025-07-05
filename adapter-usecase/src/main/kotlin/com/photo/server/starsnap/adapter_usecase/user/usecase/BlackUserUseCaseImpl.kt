package com.photo.server.starsnap.adapter_usecase.user.usecase

import com.photo.server.starsnap.adapter_infrastructure.extension.toDomainPageRequest
import com.photo.server.starsnap.adapter_infrastructure.user.repository.BlackUserRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.user.repository.UserRepositoryImpl
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import com.photo.server.starsnap.domain.user.entity.BlackUser
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.exception.user.error.exception.NotFoundUserIdException
import com.photo.server.starsnap.usecase.user.dto.BlackUserDto
import com.photo.server.starsnap.usecase.user.usecase.BlackUserUseCase
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import io.viascom.nanoid.NanoId
import java.time.LocalDateTime

@Service
class BlackUserUseCaseImpl(
    private val blackUserRepositoryImpl: BlackUserRepositoryImpl,
    private val userRepositoryImpl: UserRepositoryImpl,
    private val followUseCaseImpl: FollowUseCaseImpl
) : BlackUserUseCase {
    // 유저 차단
    override fun blackUser(user: User, blackUserId: String) {
        val blackUserData = userRepositoryImpl.findByIdOrNull(blackUserId) ?: throw NotFoundUserIdException
        val blackUser = BlackUser(
            blackUserEntity = blackUserData,
            userEntity = user,
            id = NanoId.generate(16),
            createdAt = LocalDateTime.now()
        )
        // 차단 할때 언팔로우도 같이 진행
        followUseCaseImpl.unFollow(user, blackUserId)
        blackUserRepositoryImpl.save(blackUser)
    }

    // 유저 차단 해제
    override fun unBlackUser(user: User, unBlackUserId: String) {
        val blackUser = userRepositoryImpl.findByIdOrNull(unBlackUserId) ?: throw NotFoundUserIdException
        blackUserRepositoryImpl.deleteByUserAndBlackUser(user, blackUser)
    }

    // 차단한 유저 조회
    override fun getBlackUser(size: Int, page: Int, user: User): Slice<BlackUserDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val blackUserList =
            blackUserRepositoryImpl.findSliceBy(pageRequest.toDomainPageRequest(), user)
                ?: throw RuntimeException("존재 하지 않는 차단리스트")

        return blackUserList.map {
            BlackUserDto(it.blackUserEntity.username, it.blackUserEntity.profileImageUrl)
        }
    }
}