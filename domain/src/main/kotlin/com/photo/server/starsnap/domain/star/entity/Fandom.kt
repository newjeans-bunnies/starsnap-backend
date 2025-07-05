package com.photo.server.starsnap.domain.star.entity

import com.photo.server.starsnap.domain.star.entity.base.BaseFandom
import java.time.LocalDateTime

data class Fandom(
    val totalMembers: Int = 0,
    var name: String,
    var explanation: String,
    val starGroup: StarGroup,
    override val createAt: LocalDateTime?,
    override val id: String
) : BaseFandom(id, createAt)