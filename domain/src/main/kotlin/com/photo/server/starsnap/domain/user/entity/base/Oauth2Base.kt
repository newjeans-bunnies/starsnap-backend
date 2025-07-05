package com.photo.server.starsnap.domain.user.entity.base

import java.time.LocalDateTime

abstract class Oauth2Base(
    open val id: String,
    open val createdAt: LocalDateTime?
)