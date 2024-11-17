package com.photo.server.starsnap.domain.snap.entity.base

import jakarta.persistence.*
import io.viascom.nanoid.NanoId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class BaseSnapEntity {
    @Id
    @Column(name = "id", columnDefinition = "CHAR(16)")
    open var id = NanoId.generate(16)
        protected set

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    var createdAt: LocalDateTime? = null
        protected set

    @LastModifiedDate
    @Column(name = "modified_at", columnDefinition = "DATETIME")
    open var modifiedAt: LocalDateTime? = null
        protected set
}