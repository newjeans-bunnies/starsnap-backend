package com.photo.server.starsnap.domain.auth.entity

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.annotation.Id;

@RedisHash(value = "email_verify", timeToLive = 600)
data class EmailVerifyEntity(
    @Id
    val email: String,
    val verifyCode: String
)