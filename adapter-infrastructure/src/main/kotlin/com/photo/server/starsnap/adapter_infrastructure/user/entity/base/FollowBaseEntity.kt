package com.photo.server.starsnap.adapter_infrastructure.user.entity.base

import io.viascom.nanoid.NanoId
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class FollowBaseEntity {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "CHAR(16)")
    var id: String = NanoId.generate()
        protected set

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    var createdAt: LocalDateTime? = null
        protected set
}