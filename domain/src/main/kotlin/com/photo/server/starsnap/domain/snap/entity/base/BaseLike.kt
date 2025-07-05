package com.photo.server.starsnap.domain.snap.entity.base

import java.time.LocalDateTime

abstract class BaseLike(
    open val id: String,
    open val createdAt: LocalDateTime?
)