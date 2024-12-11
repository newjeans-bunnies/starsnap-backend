package com.photo.server.starsnap.domain.snap.service

import com.photo.server.starsnap.domain.snap.error.exception.NotFoundSnapIdException
import com.photo.server.starsnap.domain.snap.repository.SnapRepository
import com.photo.server.starsnap.domain.user.error.exception.NotFoundUserIdException
import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.global.dto.SnapDto
import com.photo.server.starsnap.global.dto.toSnapDto
import com.photo.server.starsnap.global.error.exception.InvalidRoleException
import jakarta.transaction.Transactional
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DeleteSnapService(
    private val snapRepository: SnapRepository,
    private val userRepository: UserRepository,
) {
    @Transactional
    fun getDeleteSnap(page: Int, size: Int, userId: String): Slice<SnapDto> {
        val snaps = snapRepository.findSliceByStateAndUserId(false, userId) ?: throw NotFoundSnapIdException
        return snaps.map {
            it.toSnapDto()
        }
    }

    @Transactional
    fun rollbackSnap(snapId: String, userId: String): SnapDto {
        val snap = snapRepository.findByIdOrNull(snapId) ?: throw NotFoundSnapIdException
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundUserIdException
        if (user != snap.user) throw InvalidRoleException
        snap.state = true

        snapRepository.save(snap)
        return snap.toSnapDto()
    }
}