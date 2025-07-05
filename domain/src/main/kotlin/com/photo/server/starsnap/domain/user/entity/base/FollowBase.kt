package com.photo.server.starsnap.domain.user.entity.base

import java.time.LocalDateTime

abstract class FollowBase(
    open val id: String,
    open val createdAt: LocalDateTime?
)