package com.photo.server.starsnap.domain.star.entity.base

import java.time.LocalDateTime

abstract class BaseFan(
    open val id: String,
    open val createAt: LocalDateTime?
)