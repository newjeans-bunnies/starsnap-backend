package com.photo.server.starsnap.adapter_usecase.snap.usecase

import com.photo.server.starsnap.adapter_infrastructure.snap.repository.SaveRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.SnapRepositoryImpl
import com.photo.server.starsnap.domain.snap.entity.Save
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.exception.snap.error.exception.NotFoundSnapIdException
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.snap.usecase.SaveUseCase
import org.springframework.stereotype.Service
import io.viascom.nanoid.NanoId
import java.time.LocalDateTime

@Service
class SaveUseCaseImpl(
    private val saveRepositoryImpl: SaveRepositoryImpl,
    private val snapRepositoryImpl: SnapRepositoryImpl,
): SaveUseCase {
    // 스냅 저장
    override fun saveSnap(user: User, snapId: String): StatusDto {
        val snap = snapRepositoryImpl.findByIdOrNull(snapId) ?: throw NotFoundSnapIdException
        val save = Save(user, snap, id = NanoId.generate(16), saveTime = LocalDateTime.now())
        saveRepositoryImpl.save(save)
        return StatusDto("OK", 200)
    }

    // 스냅 저장 취소
    override fun unSaveSnap(userId: String, snapId: String): StatusDto {
        if (!snapRepositoryImpl.existsById(snapId)) throw NotFoundSnapIdException
        saveRepositoryImpl.deleteByUserIdAndSnapId(userId, snapId)
        return StatusDto("OK", 200)
    }
}