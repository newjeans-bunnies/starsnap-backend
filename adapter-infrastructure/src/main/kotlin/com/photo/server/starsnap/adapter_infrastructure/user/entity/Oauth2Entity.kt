package com.photo.server.starsnap.adapter_infrastructure.user.entity

import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import com.photo.server.starsnap.adapter_infrastructure.user.entity.base.Oauth2BaseEntity
import com.photo.server.starsnap.domain.auth.type.Oauth2IssuerUris
import com.photo.server.starsnap.domain.auth.type.Oauth2Type
import com.photo.server.starsnap.domain.user.entity.Oauth2

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "oauth2")
class Oauth2Entity(
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var type: Oauth2Type,
    @Column(nullable = false, columnDefinition = "VARCHAR(320)")
    var email: String,
    @Column(nullable = false , columnDefinition = "VARCHAR(100)")
    @Enumerated(EnumType.STRING)
    var issuerUris: Oauth2IssuerUris,
    @Column(nullable = false)
    var sub: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    var user: UserEntity
) : Oauth2BaseEntity() {
    fun toDomain() = Oauth2(
        type = this.type,
        email = this.email,
        sub = this.sub,
        user = this.user.toDomain(),
        id = this.id,
        createdAt = this.createdAt,
        issuerUris = this.issuerUris
    )

    companion object {
        fun fromDomain(oauth2: Oauth2) = Oauth2Entity(
            type = oauth2.type,
            email = oauth2.email,
            sub = oauth2.sub,
            user = UserEntity.fromDomain(oauth2.user),
            issuerUris = oauth2.issuerUris
        )
    }

}