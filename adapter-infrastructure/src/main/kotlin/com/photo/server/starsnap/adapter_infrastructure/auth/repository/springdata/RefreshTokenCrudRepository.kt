package com.photo.server.starsnap.adapter_infrastructure.auth.repository.springdata

import com.photo.server.starsnap.adapter_infrastructure.auth.entity.RefreshTokenEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenCrudRepository: CrudRepository<RefreshTokenEntity, String> {
    fun findByToken(token: String): RefreshTokenEntity?
}