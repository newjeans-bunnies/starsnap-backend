package com.photo.server.starsnap.adapter_infrastructure.star.repository

import com.photo.server.starsnap.adapter_infrastructure.star.entity.FanEntity
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.star.repository.springdata.FanCrudRepository
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.star.entity.Fan
import com.photo.server.starsnap.domain.star.repository.FanRepository
import com.photo.server.starsnap.domain.user.entity.User

@Repository
class FanRepositoryImpl(
    private val fanCrudRepository: FanCrudRepository
): FanRepository {
    override fun existsByUserAndStarId(user: User, starId: String): Boolean {
        val userEntity = UserEntity.fromDomain(user)
        return fanCrudRepository.existsByUserIdAndStarId(userEntity.id, starId)
    }

    override fun deleteByUserAndStarId(user: User, starId: String) {
        val userEntity = UserEntity.fromDomain(user)
        return fanCrudRepository.deleteByUserIdAndStarId(userEntity.id, starId)
    }

    override fun save(fan: Fan): Fan {
        val fanEntity = FanEntity.fromDomain(fan)
        return fanCrudRepository.save(fanEntity).toDomain()
    }
}