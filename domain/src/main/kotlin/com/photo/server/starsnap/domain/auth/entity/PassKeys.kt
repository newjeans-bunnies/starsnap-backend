package com.photo.server.starsnap.domain.auth.entity

import com.photo.server.starsnap.domain.user.entity.User


data class PassKeys(
    val id: String,
    val credPublicKey: String,
    val webauthnUserId: String,
    val counter: String,
    val backupEligible: Boolean,
    val backupStatus: Boolean,
    val transports: String,
    val createdAt: String,
    val lastUsed: String,
    val user: User
)