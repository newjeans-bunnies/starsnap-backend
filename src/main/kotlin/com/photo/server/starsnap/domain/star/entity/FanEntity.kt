package com.photo.server.starsnap.domain.star.entity

import com.photo.server.starsnap.domain.star.entity.base.BaseFanEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "fan")
class FanEntity(
    user: UserEntity, star: StarEntity
) : BaseFanEntity() {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "CHAR(16)")
    var user: UserEntity = user
        protected set

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "star_id", nullable = false, columnDefinition = "CHAR(16)")
    var star: StarEntity = star
        protected set
}