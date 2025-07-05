package com.photo.server.starsnap.domain.star.entity

import com.photo.server.starsnap.domain.star.entity.base.BaseStarGroup
import com.photo.server.starsnap.domain.star.type.StarGroupType
import java.time.LocalDateTime

data class StarGroup(
    var name: String,
    var debutDate: LocalDateTime,
    val starGroupType: StarGroupType,
    var explanation: String? = null,
    val imageKey: String? = null,
    override val id: String = ""
) : BaseStarGroup(id)