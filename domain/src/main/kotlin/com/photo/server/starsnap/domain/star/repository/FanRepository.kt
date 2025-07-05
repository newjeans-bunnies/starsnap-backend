package com.photo.server.starsnap.domain.star.repository

import com.photo.server.starsnap.domain.star.entity.Fan
import com.photo.server.starsnap.domain.user.entity.User

interface FanRepository {
    fun existsByUserAndStarId(user: User, starId: String): Boolean
    fun deleteByUserAndStarId(user: User, starId: String)
    fun save(fan: Fan): Fan
}