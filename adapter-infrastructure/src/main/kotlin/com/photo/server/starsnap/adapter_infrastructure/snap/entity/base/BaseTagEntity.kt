package com.photo.server.starsnap.adapter_infrastructure.snap.entity.base

import io.viascom.nanoid.NanoId
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class BaseTagEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "VARCHAR(16)")

    open var id = NanoId.generate(16)
        protected set
}