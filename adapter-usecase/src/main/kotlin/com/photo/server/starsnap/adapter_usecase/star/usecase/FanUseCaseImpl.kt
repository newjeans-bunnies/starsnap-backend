package com.photo.server.starsnap.adapter_usecase.star.usecase

import com.photo.server.starsnap.adapter_infrastructure.star.repository.FanRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.star.repository.StarRepositoryImpl
import com.photo.server.starsnap.domain.star.entity.Fan
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.exception.star.error.exception.NotConnectedFanException
import com.photo.server.starsnap.exception.star.error.exception.NotFoundStarIdException
import org.springframework.stereotype.Service
import io.viascom.nanoid.NanoId
import java.time.LocalDateTime

@Service
class FanUseCaseImpl(
    private val fanRepositoryImpl: FanRepositoryImpl,
    private val starRepositoryImpl: StarRepositoryImpl
) {
    // 팬 가입
    fun joinFan(userData: User, starId: String) {
        val star = starRepositoryImpl.findByIdOrNull(starId) ?: throw NotFoundStarIdException
        val fan = Fan(userData, star, id = NanoId.generate(16), createAt = LocalDateTime.now())
        fanRepositoryImpl.save(fan)
    }

    // 팬 가입해제
    fun disconnectFan(userData: User, starId: String) {
        if (!starRepositoryImpl.existsById(starId)) throw NotFoundStarIdException
        if (!fanRepositoryImpl.existsByUserAndStarId(userData, starId)) throw NotConnectedFanException
        fanRepositoryImpl.deleteByUserAndStarId(userData, starId)
    }
}