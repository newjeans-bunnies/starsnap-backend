package com.photo.server.starsnap.domain.star.entity

import com.photo.server.starsnap.domain.star.entity.base.BaseStar
import com.photo.server.starsnap.domain.star.type.GenderType
import java.time.LocalDateTime

data class Star(
    var name: String,
    var gender: GenderType,
    var birthday: LocalDateTime,
    var explanation: String? = null,
    var starGroup: StarGroup? = null,
    var nickname: String,
    val imageKey: String? = null,
    override val id: String = ""
) : BaseStar(id)