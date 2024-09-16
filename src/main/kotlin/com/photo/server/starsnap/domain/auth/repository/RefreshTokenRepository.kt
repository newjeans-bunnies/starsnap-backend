package com.photo.server.starsnap.domain.auth.repository

import com.photo.server.starsnap.domain.auth.entity.RefreshTokenEntity
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository: CrudRepository<RefreshTokenEntity, String> {
    fun findByToken(token: String): RefreshTokenEntity?
}