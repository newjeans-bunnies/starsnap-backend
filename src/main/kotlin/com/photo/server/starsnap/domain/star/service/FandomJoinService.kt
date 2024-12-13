package com.photo.server.starsnap.domain.star.service

import com.photo.server.starsnap.domain.star.entity.FandomJoinEntity
import com.photo.server.starsnap.domain.star.error.exception.NotFoundFandomIdException
import com.photo.server.starsnap.domain.star.repository.FandomJoinRepository
import com.photo.server.starsnap.domain.star.repository.FandomRepository
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.dto.StatusDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FandomJoinService(
    private val fandomJoinRepository: FandomJoinRepository,
    private val fandomRepository: FandomRepository,
) {
    fun joinFandom(user: UserEntity, fandomId: String): StatusDto {
        val fandom = fandomRepository.findByIdOrNull(fandomId) ?: throw NotFoundFandomIdException
        val fandomJoin = FandomJoinEntity(user, fandom)
        fandomJoinRepository.save(fandomJoin)
        return StatusDto("OK", 200)
    }

    fun disconnectFandom(user: UserEntity, fandomId: String): StatusDto {
        val fandom = fandomRepository.findByIdOrNull(fandomId) ?: throw NotFoundFandomIdException
        fandomJoinRepository.deleteByFandomIdAndUserId(fandom, user)
        return StatusDto("OK", 200)
    }
}