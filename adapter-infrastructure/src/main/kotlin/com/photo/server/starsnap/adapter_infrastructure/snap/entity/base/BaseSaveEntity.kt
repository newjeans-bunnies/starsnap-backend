package com.photo.server.starsnap.adapter_infrastructure.snap.entity.base

import io.viascom.nanoid.NanoId
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class BaseSaveEntity {
    @Id
    @Column(name = "id", columnDefinition = "CHAR(16)")
    open var id = NanoId.generate(16)
        protected set

    @CreatedDate
    @Column(name = "save_time", nullable = false, updatable = false, columnDefinition = "DATETIME")
    var saveTime: LocalDateTime? = null
        protected set

}