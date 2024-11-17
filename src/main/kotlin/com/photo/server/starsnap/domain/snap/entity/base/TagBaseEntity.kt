package com.photo.server.starsnap.domain.snap.entity.base

import io.viascom.nanoid.NanoId
import jakarta.persistence.*

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class TagBaseEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "VARCHAR(16)")

    open var id = NanoId.generate(16)
        protected set
}