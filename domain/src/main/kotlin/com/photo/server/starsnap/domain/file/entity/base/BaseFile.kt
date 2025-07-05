package com.photo.server.starsnap.domain.file.entity.base

import java.time.LocalDateTime

abstract class BaseFile(
    open val createdAt: LocalDateTime?
)