package com.photo.server.starsnap.domain.file.entity.base

import com.photo.server.starsnap.domain.file.type.Status
import java.time.LocalDateTime

abstract class BaseFile(
    open val createdAt: LocalDateTime?,
    open val status: Status
)