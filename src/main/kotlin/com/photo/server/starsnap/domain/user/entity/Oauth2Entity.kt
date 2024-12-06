package com.photo.server.starsnap.domain.user.entity

import com.photo.server.starsnap.domain.auth.type.Oauth2
import com.photo.server.starsnap.domain.user.entity.base.Oauth2BaseEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "oauth2")
class Oauth2Entity(
    type: Oauth2,
    email: String,
    sub: String,
    userId: UserEntity
) : Oauth2BaseEntity() {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var type = type
        protected set

    @Column(nullable = false, columnDefinition = "VARCHAR(320)")
    var email = email
        protected set

    @Column(nullable = false)
    var sub = sub
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity = userId
        protected set
}