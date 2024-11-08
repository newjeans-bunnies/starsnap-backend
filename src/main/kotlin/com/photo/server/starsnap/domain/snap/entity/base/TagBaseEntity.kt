package com.photo.server.starsnap.domain.snap.entity.base

import io.viascom.nanoid.NanoId
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class TagBaseEntity {
    @Id
    open var id = NanoId.generate(16)
        protected set
}