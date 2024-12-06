package com.photo.server.starsnap.domain.star.entity

import com.photo.server.starsnap.domain.star.entity.base.BaseFandomJoinEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import jakarta.persistence.*


@Entity
@Table(name = "fandom_join")
class FandomJoinEntity(
    user: UserEntity,
    fandom: FandomEntity
) : BaseFandomJoinEntity() {
    @ManyToOne(fetch = FetchType.LAZY, cascade = [(CascadeType.REMOVE)])
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "CHAR(16)")
    val user: UserEntity = user

    @ManyToOne(fetch = FetchType.LAZY, cascade = [(CascadeType.REMOVE)])
    @JoinColumn(name = "fandom_id", nullable = false, columnDefinition = "CHAR(16)")
    val fandomId: FandomEntity = fandom
    val fandom: FandomEntity = fandom
}