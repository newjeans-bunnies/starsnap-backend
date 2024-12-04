package com.photo.server.starsnap.domain.star.service

import com.photo.server.starsnap.domain.star.entity.FanEntity
import com.photo.server.starsnap.domain.star.repository.FanRepository
import com.photo.server.starsnap.domain.star.repository.StarRepository
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
        val user = userRepository.findByIdOrNull(userId) ?: throw RuntimeException("존재 하지않는 UserId")
        val star = starRepository.findByIdOrNull(starId) ?: throw RuntimeException("존재 하지 않는 StarId")
        val fan = FanEntity(user, star)
        fanRepository.save(fan)
    }

    fun disconnectFan(userId: String, starId: String) {
        if(!userRepository.existsById(userId)) throw RuntimeException("존재 하지 않는 UserId")
        if(!starRepository.existsById(starId)) throw RuntimeException("존재 하지 않는 StarId")
        if(!fanRepository.existsByUserIdAndStarId(userId, starId)) throw RuntimeException("팬으로 연결 되어 있지 않음")
        fanRepository.deleteByUserIdAndStarId(userId, starId)
    }
}