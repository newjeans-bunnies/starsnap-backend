package com.photo.server.starsnap.adapter_infrastructure.auth.repository

import com.photo.server.starsnap.adapter_infrastructure.auth.entity.RefreshTokenEntity
import com.photo.server.starsnap.adapter_infrastructure.auth.repository.springdata.RefreshTokenCrudRepository
import com.photo.server.starsnap.domain.auth.entity.RefreshToken
import com.photo.server.starsnap.domain.auth.repository.RefreshTokenRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class RefreshTokenRepositoryImpl(
    private val refreshTokenCrudRepository: RefreshTokenCrudRepository
): RefreshTokenRepository {
    override fun save(token: RefreshToken): RefreshToken {
        val tokenEntity = RefreshTokenEntity.fromDomain(token)
        val savedEntity = refreshTokenCrudRepository.save(tokenEntity)
        return savedEntity.toDomain()
    }

    override fun findByToken(token: String): RefreshToken? {
        val findEntity = refreshTokenCrudRepository.findByToken(token)
        return findEntity?.toDomain()
    }

    override fun delete(token: RefreshToken) {
        val tokenEntity = RefreshTokenEntity.fromDomain(token)
        refreshTokenCrudRepository.delete(tokenEntity)
    }

    override fun findByIdOrNull(id: String): RefreshToken? {
        val findEntity = refreshTokenCrudRepository.findByIdOrNull(id)
        return findEntity?.toDomain()
    }
}