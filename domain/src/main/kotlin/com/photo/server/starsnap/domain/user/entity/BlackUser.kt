package com.photo.server.starsnap.domain.user.entity

import com.photo.server.starsnap.domain.user.entity.base.BlackUserBase
import java.time.LocalDateTime

data class BlackUser(
    val blackUserEntity: User,
    val userEntity: User,
    override val createdAt: LocalDateTime?,
    override val id: String
) : BlackUserBase(id, createdAt)