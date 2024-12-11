package com.photo.server.starsnap.domain.snap.service

import com.photo.server.starsnap.domain.snap.entity.LikeEntity
import com.photo.server.starsnap.domain.snap.error.exception.NotFoundSnapIdException
import com.photo.server.starsnap.domain.snap.repository.LikeRepository
import com.photo.server.starsnap.domain.snap.repository.SnapRepository
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.dto.StatusDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val likeRepository: LikeRepository,
    private val snapRepository: SnapRepository
) {
    fun likeSnap(user: UserEntity, snapId: String): StatusDto {
        val snap = snapRepository.findByIdOrNull(snapId) ?: throw NotFoundSnapIdException
        val like = LikeEntity(user, snap)
        likeRepository.save(like)
        return StatusDto("OK", 200)
    }

    fun unlikeSnap(user: UserEntity, snapId: String): StatusDto {
        val snap = snapRepository.findByIdOrNull(snapId) ?: throw NotFoundSnapIdException
        likeRepository.deleteBySnapAndUser(snap, user)
        return StatusDto("OK", 200)
    }
}