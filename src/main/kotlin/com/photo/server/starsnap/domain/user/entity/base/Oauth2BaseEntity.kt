package com.photo.server.starsnap.domain.user.entity.base

import io.viascom.nanoid.NanoId
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class Oauth2BaseEntity {
    @Id
    @Column(name = "id", columnDefinition = "CHAR(16)", updatable = false)
    open var id: String = NanoId.generate(16)
        protected set

    @CreatedDate
    @Column(name = "created_at", columnDefinition = "DATETIME")
    open var createdAt: LocalDateTime? = null
        protected set
}