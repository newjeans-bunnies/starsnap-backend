package com.photo.server.starsnap.adapter_infrastructure.user.entity

import jakarta.persistence.*
import com.photo.server.starsnap.adapter_infrastructure.user.entity.base.BlackUserBaseEntity
import com.photo.server.starsnap.domain.user.entity.BlackUser

@Entity
@Table(name = "black_user")
class BlackUserEntity(
    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "black_user_id", nullable = false, updatable = false, columnDefinition = "CHAR(16)")
    val blackUserEntity: UserEntity,
    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id", nullable = false, updatable = false, columnDefinition = "CHAR(16)")
    val userEntity: UserEntity
) : BlackUserBaseEntity() {
    fun toDomain() = BlackUser(
        blackUserEntity = this.blackUserEntity.toDomain(),
        userEntity = this.userEntity.toDomain(),
        createdAt = this.createdAt,
        id = this.id
    )

    companion object {
        fun fromDomain(blackUser: BlackUser) = BlackUserEntity(
            blackUserEntity = UserEntity.fromDomain(blackUser.blackUserEntity),
            userEntity = UserEntity.fromDomain(blackUser.userEntity)
        )
    }
}