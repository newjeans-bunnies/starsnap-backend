package com.photo.server.starsnap.domain.snap.entity.base

import java.time.LocalDateTime

abstract class BaseComment(
    open val id: String,
    open val createdAt: LocalDateTime?,
    open val modifiedAt: LocalDateTime?
)