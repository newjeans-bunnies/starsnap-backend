package com.photo.server.starsnap.domain.auth

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.annotation.Id;

@RedisHash(value = "refresh", timeToLive = 604800)
data class RefreshTokenEntity(
    @Id
    val id: String,
    val token: String
)
