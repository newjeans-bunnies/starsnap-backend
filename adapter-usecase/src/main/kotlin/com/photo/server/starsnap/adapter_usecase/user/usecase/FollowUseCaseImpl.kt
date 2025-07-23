package com.photo.server.starsnap.adapter_usecase.user.usecase

import com.photo.server.starsnap.adapter_infrastructure.global.extension.toDomainPageRequest
import com.photo.server.starsnap.adapter_infrastructure.user.repository.FollowRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.user.repository.UserRepositoryImpl
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.exception.user.error.exception.NotFoundFollowIdException
import com.photo.server.starsnap.exception.user.error.exception.NotFoundUserIdException
import com.photo.server.starsnap.usecase.user.dto.FollowerDto
import com.photo.server.starsnap.usecase.user.dto.FollowingDto
import com.photo.server.starsnap.usecase.user.dto.toFollowDto
import com.photo.server.starsnap.usecase.user.dto.toFollowerDto
import com.photo.server.starsnap.usecase.user.usecase.FollowUseCase
import com.photo.server.starsnap.domain.user.entity.Follow
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import io.viascom.nanoid.NanoId
import java.time.LocalDateTime

@Service
class FollowUseCaseImpl(
    private val userRepositoryImpl: UserRepositoryImpl,
    private val followRepositoryImpl: FollowRepositoryImpl
): FollowUseCase {

    private val logging = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    override fun follow(user: User, followUserId: String) {
        val followUser = userRepositoryImpl.findByIdOrNull(followUserId) ?: throw NotFoundUserIdException

        val followData = Follow(
            followerUser = user,
            followingUser = followUser,
            id = NanoId.generate(16),
            createdAt = LocalDateTime.now(),
        )

        followRepositoryImpl.save(followData)
    }

    @Transactional
    override fun unFollow(user: User, followUserId: String) {

        val followData = followRepositoryImpl.findByFollowUserAndFollowerUserId(user, followUserId) ?: throw NotFoundFollowIdException

        followRepositoryImpl.delete(followData)
    }

    override fun getFollowing(user: User, page: Int, size: Int): Slice<FollowingDto> {

        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )

        val followData = followRepositoryImpl.getFollowing(pageRequest.toDomainPageRequest(), user) ?: throw NotFoundUserIdException

        return followData.map {
            it.toFollowDto()
        }
    }

    override fun getFollower(user: User, page: Int, size: Int): Slice<FollowerDto> {

        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )

        val followData = followRepositoryImpl.getFollowers(pageRequest.toDomainPageRequest(), user) ?: throw NotFoundUserIdException

        return followData.map {
            it.toFollowerDto()
        }

    }
}