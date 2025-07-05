package com.photo.server.starsnap.adapter_infrastructure.star.repository.springdata

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.star.entity.FanEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity

@Repository
interface FanCrudRepository: CrudRepository<FanEntity, String> {
    fun existsByUserIdAndStarId(user: String, starId: String): Boolean
    fun deleteByUserIdAndStarId(user: String, starId: String)
}