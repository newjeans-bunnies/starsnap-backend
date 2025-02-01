package com.photo.server.starsnap.domain.user.entity

import com.photo.server.starsnap.domain.user.entity.base.BlackUserBaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "black_user")
class BlackUserEntity(
    blackUserEntity: UserEntity,
    userEntity: UserEntity
) : BlackUserBaseEntity() {
    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id", nullable = false, updatable = false, columnDefinition = "CHAR(16)")
    var user: UserEntity = userEntity

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "black_user_id", nullable = false, updatable = false, columnDefinition = "CHAR(16)")
    var blackUser: UserEntity = blackUserEntity
}