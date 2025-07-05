package com.photo.server.starsnap.adapter_infrastructure.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "email_token", timeToLive = 600)
data class EmailTokenEntity(
    @Id
    val email: String,
    @Indexed
    val token: String
)