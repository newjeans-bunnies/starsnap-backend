package com.photo.server.starsnap.adapter_infrastructure.star.entity

import jakarta.persistence.*
import com.photo.server.starsnap.adapter_infrastructure.star.entity.base.FanBaseEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.star.entity.Fan

@Entity
@Table(name = "fan")
class FanEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "CHAR(16)")
    val user: UserEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "star_id", nullable = false, columnDefinition = "CHAR(16)")
    val star: StarEntity
) : FanBaseEntity() {
    fun toDomain() = Fan(
        user = this.user.toDomain(),
        star = this.star.toDomain(),
        id = this.id,
        createAt = this.createAt
    )

    companion object {
        fun fromDomain(fan: Fan) = FanEntity(
            user = UserEntity.fromDomain(fan.user),
            star = StarEntity.fromDomain(fan.star)
        )
    }
}