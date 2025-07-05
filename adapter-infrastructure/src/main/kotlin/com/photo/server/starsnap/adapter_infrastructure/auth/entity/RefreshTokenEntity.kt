package com.photo.server.starsnap.adapter_infrastructure.auth.entity

import com.photo.server.starsnap.domain.auth.entity.RefreshToken
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "refresh", timeToLive = 604800)
data class RefreshTokenEntity(
    @Id
    val id: String,
    @Indexed
    val token: String
){
    fun toDomain() = RefreshToken(
        id = id,
        token = token
    )

    companion object {
        fun fromDomain(refreshToken: RefreshToken) = RefreshTokenEntity(
            id = refreshToken.id,
            token = refreshToken.token
        )
    }
}