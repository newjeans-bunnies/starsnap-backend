package com.photo.server.starsnap.adapter_infrastructure.file.entity.base

import com.photo.server.starsnap.domain.file.type.Status
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class BaseFileEntity {
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    open var createdAt: LocalDateTime? = null
        protected set

    @Column(name = "file_extension", nullable = false)
    @Enumerated(EnumType.STRING)
    open var status: Status = Status.INIT
        protected set
}