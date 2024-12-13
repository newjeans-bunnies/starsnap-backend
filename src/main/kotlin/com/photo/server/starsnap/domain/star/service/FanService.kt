package com.photo.server.starsnap.domain.star.service

import com.photo.server.starsnap.domain.star.entity.FanEntity
import com.photo.server.starsnap.domain.star.error.exception.NotConnectedFanException
import com.photo.server.starsnap.domain.star.error.exception.NotFoundStarIdException
import com.photo.server.starsnap.domain.star.repository.FanRepository
import com.photo.server.starsnap.domain.star.repository.StarRepository
import com.photo.server.starsnap.domain.user.error.exception.NotFoundUserIdException
import com.photo.server.starsnap.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FanService(
    private val fanRepository: FanRepository,
    private val userRepository: UserRepository,
    private val starRepository: StarRepository
) {
    fun joinFan(userId: String, starId: String) {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundUserIdException
        val star = starRepository.findByIdOrNull(starId) ?: throw NotFoundStarIdException
        val fan = FanEntity(user, star)
        fanRepository.save(fan)
    }

    fun disconnectFan(userId: String, starId: String) {
        if(!userRepository.existsById(userId)) throw NotFoundUserIdException
        if(!starRepository.existsById(starId)) throw NotFoundStarIdException
        if(!fanRepository.existsByUserIdAndStarId(userId, starId)) throw NotConnectedFanException
        fanRepository.deleteByUserIdAndStarId(userId, starId)
    }
}