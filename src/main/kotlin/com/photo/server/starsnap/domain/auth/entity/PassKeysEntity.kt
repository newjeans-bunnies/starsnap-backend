package com.photo.server.starsnap.domain.auth.entity

import com.photo.server.starsnap.domain.user.entity.UserEntity
import jakarta.persistence.*
import io.viascom.nanoid.NanoId

@Entity
@Table(name = "passkeys")
data class PassKeysEntity(
    @Id
    val id: String = NanoId.generate(16),
    @Column
    val credPublicKey: String,
    @Column
    val webauthnUserId: String,
    @Column
    val counter: String,
    @Column
    val backupEligible: Boolean,
    @Column
    val backupStatus: Boolean,
    @Column
    val transports: String,
    @Column(updatable = false)
    val createdAt: String,
    @Column
    val lastUsed: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity
)