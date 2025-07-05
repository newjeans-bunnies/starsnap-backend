package com.photo.server.starsnap.adapter_usecase.star.usecase

import com.photo.server.starsnap.adapter_infrastructure.star.repository.FandomRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.star.repository.StarGroupRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.star.repository.FandomJoinRepositoryImpl
import com.photo.server.starsnap.domain.star.entity.FandomJoin
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.exception.star.error.exception.NotFoundFandomIdException
import com.photo.server.starsnap.exception.star.error.exception.NotFoundFandomJoinException
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.star.usecase.FandomJoinUseCase
import org.springframework.stereotype.Service
import io.viascom.nanoid.NanoId
import java.time.LocalDateTime

@Service
class FandomJoinUseCaseImpl(
    private val fandomRepositoryImpl: FandomRepositoryImpl,
    private val starGroupRepositoryImpl: StarGroupRepositoryImpl,
    private val fandomJoinRepositoryImpl: FandomJoinRepositoryImpl
): FandomJoinUseCase {
    // 팬덤 - 유저 연결
    override fun joinFandom(
        user: User,
        fandomId: String
    ): StatusDto {
        val fandom = fandomRepositoryImpl.findByIdOrNull(fandomId) ?: throw NotFoundFandomIdException
        val fandomJoin = FandomJoin(
            id = NanoId.generate(16),
            user = user,
            fandom = fandom,
            joinDate = LocalDateTime.now()
        )
        fandomJoinRepositoryImpl.save(fandomJoin)

        return StatusDto("Ok", 200)
    }

    // 팬덤 - 유저 연결 해제
    override fun disconnectFandom(
        user: User,
        fandomId: String
    ): StatusDto {
        val fandom = fandomRepositoryImpl.findByIdOrNull(fandomId) ?: throw NotFoundFandomIdException

        val fandomJoin = fandomJoinRepositoryImpl.findByFandomAndUser(fandom, user) ?: throw NotFoundFandomJoinException

        fandomJoinRepositoryImpl.delete(fandomJoin)

        return StatusDto("Ok", 201)
    }

}