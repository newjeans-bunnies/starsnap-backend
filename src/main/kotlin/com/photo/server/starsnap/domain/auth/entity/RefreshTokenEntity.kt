package com.photo.server.starsnap.domain.auth.entity

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "refresh", timeToLive = 604800)
data class RefreshTokenEntity(
    @Id
    val id: String,
    @Indexed
    val token: String
)