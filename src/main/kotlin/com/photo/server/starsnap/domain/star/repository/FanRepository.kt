package com.photo.server.starsnap.domain.star.repository

import com.photo.server.starsnap.domain.star.entity.FanEntity
import org.springframework.data.repository.CrudRepository

interface FanRepository: CrudRepository<FanEntity, String> {
    fun existsByUserIdAndStarId(userId: String, starId: String): Boolean
    fun deleteByUserIdAndStarId(userId: String, starId: String)
}