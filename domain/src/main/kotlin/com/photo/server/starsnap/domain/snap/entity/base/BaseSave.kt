package com.photo.server.starsnap.domain.snap.entity.base

import java.time.LocalDateTime

abstract class BaseSave(
    open val id: String,
    open val saveTime: LocalDateTime?
)