package com.photo.server.starsnap.domain.snap.service

import com.photo.server.starsnap.domain.snap.entity.SaveEntity
import com.photo.server.starsnap.domain.snap.error.exception.NotFoundSnapIdException
import com.photo.server.starsnap.domain.snap.repository.SaveRepository
import com.photo.server.starsnap.domain.snap.repository.SnapRepository
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.dto.StatusDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SaveService(
    private val saveRepository: SaveRepository,
    private val snapRepository: SnapRepository
) {
    fun saveSnap(user: UserEntity, snapId: String): StatusDto {
        val snap = snapRepository.findByIdOrNull(snapId) ?: throw NotFoundSnapIdException
        val save = SaveEntity(user, snap)
        saveRepository.save(save)
        return StatusDto("OK", 200)
    }

    fun unSaveSnap(userId: String, snapId: String): StatusDto {
        if (!snapRepository.existsById(snapId)) throw NotFoundSnapIdException
        saveRepository.deleteByUserIdAndSnapId(userId, snapId)
        return StatusDto("OK", 200)
    }
}