package com.photo.server.starsnap.domain.auth.entity

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.annotation.Id;

@RedisHash(value = "email_token", timeToLive = 600)
data class EmailTokenEntity(
    @Id
    val email: String,
    val token: String
)