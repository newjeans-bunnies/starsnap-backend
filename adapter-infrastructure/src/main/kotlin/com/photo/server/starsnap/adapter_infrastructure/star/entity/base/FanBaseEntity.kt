package com.photo.server.starsnap.adapter_infrastructure.star.entity.base

import io.viascom.nanoid.NanoId
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class FanBaseEntity {
    @Id
    @Column(name = "id", columnDefinition = "CHAR(16)")
    open var id = NanoId.generate(16)
        protected set

    @CreatedDate
    @Column(name = "create_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    var createAt: LocalDateTime? = null
        protected set

}