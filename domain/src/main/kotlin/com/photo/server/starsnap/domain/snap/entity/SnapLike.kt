package com.photo.server.starsnap.domain.snap.entity

import com.photo.server.starsnap.domain.snap.entity.base.BaseLike
import com.photo.server.starsnap.domain.user.entity.User
import java.time.LocalDateTime

data class SnapLike(
    val user: User,
    val snap: Snap,
    override val createdAt: LocalDateTime?,
    override val id: String
) : BaseLike(id, createdAt)