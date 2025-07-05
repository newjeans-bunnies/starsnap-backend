package com.photo.server.starsnap.adapter_infrastructure.star.entity

import jakarta.persistence.*
import com.photo.server.starsnap.adapter_infrastructure.star.entity.base.FandomJoinBaseEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.star.entity.FandomJoin

@Entity
@Table(name = "fandom_join")
class FandomJoinEntity(
    @ManyToOne(fetch = FetchType.LAZY, cascade = [(CascadeType.REMOVE)])
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "CHAR(16)")
    val user: UserEntity,
    @ManyToOne(fetch = FetchType.LAZY, cascade = [(CascadeType.REMOVE)])
    @JoinColumn(name = "fandom_id", nullable = false, columnDefinition = "CHAR(16)")
    val fandom: FandomEntity
) : FandomJoinBaseEntity() {

    fun toDomain() = FandomJoin(
        user = this.user.toDomain(),
        fandom = this.fandom.toDomain(),
        id = this.id,
        joinDate = this.joinDate
    )


    companion object {
        fun fromDomain(fandomJoin: FandomJoin) = FandomJoinEntity(
            user = UserEntity.fromDomain(fandomJoin.user),
            fandom = FandomEntity.fromDomain(fandomJoin.fandom)
        )
    }

    @PostRemove
    fun removeFandomJoin() {
        fandom.totalMembers -= 1
    }

    @PrePersist
    fun createFandomJoin() {
        fandom.totalMembers += 1
    }
}