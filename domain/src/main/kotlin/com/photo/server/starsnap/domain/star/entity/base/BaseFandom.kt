package com.photo.server.starsnap.domain.star.entity.base

import java.time.LocalDateTime

abstract class BaseFandom (
    open val id: String,
    open val createAt: LocalDateTime?
)