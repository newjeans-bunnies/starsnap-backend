package com.photo.server.starsnap.domain.user.entity.base

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime
import io.viascom.nanoid.NanoId
import org.springframework.data.annotation.CreatedDate

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class FollowBaseEntity {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "CHAR(16)")
    var id: String = NanoId.generate()
        protected set

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set
}