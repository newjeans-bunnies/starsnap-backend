package com.photo.server.starsnap.domain.star.entity

import com.photo.server.starsnap.domain.star.entity.base.BaseFandomJoin
import com.photo.server.starsnap.domain.user.entity.User
import java.time.LocalDateTime

data class FandomJoin(
    val user: User,
    val fandom: Fandom,
    override val id: String,
    override val joinDate: LocalDateTime?
) : BaseFandomJoin(id ,joinDate)