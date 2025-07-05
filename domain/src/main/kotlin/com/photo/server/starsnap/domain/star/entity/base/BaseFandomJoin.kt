package com.photo.server.starsnap.domain.star.entity.base

import java.time.LocalDateTime

abstract class BaseFandomJoin (
    open val id: String,
    open val joinDate: LocalDateTime?
)