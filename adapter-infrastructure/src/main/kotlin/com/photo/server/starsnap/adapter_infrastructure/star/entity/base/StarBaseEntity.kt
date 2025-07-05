package com.photo.server.starsnap.adapter_infrastructure.star.entity.base

import io.viascom.nanoid.NanoId
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class StarBaseEntity {
    @Id
    @Column(name = "id", columnDefinition = "CHAR(16)")
    open var id = NanoId.generate(16)
        protected set
}