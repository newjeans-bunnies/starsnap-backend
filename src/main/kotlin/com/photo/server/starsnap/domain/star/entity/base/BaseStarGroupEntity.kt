package com.photo.server.starsnap.domain.star.entity.base

import io.viascom.nanoid.NanoId
import jakarta.persistence.*

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class BaseStarGroupEntity {
    @Id
    @Column(name = "id", columnDefinition = "CHAR(16)")
    open var id = NanoId.generate(16)
        protected set
}