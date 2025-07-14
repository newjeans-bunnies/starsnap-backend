package com.photo.server.starsnap.adapter_infrastructure.star

import com.photo.server.starsnap.adapter_infrastructure.star.entity.FanEntity
import com.photo.server.starsnap.adapter_infrastructure.star.entity.FandomEntity
import com.photo.server.starsnap.adapter_infrastructure.star.entity.FandomJoinEntity
import com.photo.server.starsnap.adapter_infrastructure.star.entity.StarEntity
import com.photo.server.starsnap.adapter_infrastructure.star.entity.StarGroupEntity
import com.photo.server.starsnap.adapter_infrastructure.user.UserMapper.toUser
import com.photo.server.starsnap.domain.star.entity.Fan
import com.photo.server.starsnap.domain.star.entity.Fandom
import com.photo.server.starsnap.domain.star.entity.FandomJoin
import com.photo.server.starsnap.domain.star.entity.Star
import com.photo.server.starsnap.domain.star.entity.StarGroup

object StarMapper {
    fun FandomEntity.toFandom() = Fandom(
        id = this.id,
        name = this.name,
        explanation = this.explanation,
        totalMembers = this.totalMembers,
        createAt = this.createAt,
        starGroup = this.starGroup.toStarGroup()
    )

    fun FandomJoinEntity.toFandomJoin(
    ) = FandomJoin(
        id = this.id,
        user = this.user.toUser(),
        fandom = this.fandom.toFandom(),
        joinDate = this.joinDate
    )

    fun FanEntity.toFan() = Fan(
        id = this.id,
        user = this.user.toUser(),
        star = this.star.toStar(),
        createAt = this.createAt
    )

    fun StarEntity.toStar() = Star(
        id = this.id,
        name = this.name,
        gender = this.gender,
        birthday = this.birthday,
        explanation = this.explanation,
        nickname = this.nickname,
        imageKey = this.imageKey,
        starGroup = this.starGroup?.toStarGroup(),
    )

    fun StarGroupEntity.toStarGroup() = StarGroup(
        id = this.id,
        name = this.name,
        debutDate = this.debutDate,
        starGroupType = this.starGroupType
    )

}