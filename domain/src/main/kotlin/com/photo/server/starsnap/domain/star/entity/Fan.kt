package com.photo.server.starsnap.domain.star.entity

import com.photo.server.starsnap.domain.star.entity.base.BaseFan
import com.photo.server.starsnap.domain.user.entity.User
import java.time.LocalDateTime

data class Fan(
    val user: User,
    val star: Star,
    override val id: String,
    override val createAt: LocalDateTime?
) : BaseFan(id, createAt)