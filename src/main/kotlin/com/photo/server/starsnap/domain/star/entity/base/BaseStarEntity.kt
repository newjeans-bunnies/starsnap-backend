package com.photo.server.starsnap.domain.star.entity.base

import jakarta.persistence.*
import io.viascom.nanoid.NanoId

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class BaseStarEntity {
    @Id
    @Column(name = "id", columnDefinition = "CHAR(16)")
    open var id = NanoId.generate(16)
        protected set
}