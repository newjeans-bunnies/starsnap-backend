package com.photo.server.starsnap.domain.star.entity.base

import io.viascom.nanoid.NanoId
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class BaseFandomEntity {
    @Id
    @Column(name = "id", columnDefinition = "CHAR(16)")
    open var id = NanoId.generate(16)
        protected set

    @CreatedDate
    @Column(name = "create_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    var createAt: LocalDateTime? = null
        protected set
}