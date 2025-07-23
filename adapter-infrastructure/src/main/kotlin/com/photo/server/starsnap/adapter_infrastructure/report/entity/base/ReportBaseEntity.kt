package com.photo.server.starsnap.adapter_infrastructure.report.entity.base

import io.viascom.nanoid.NanoId
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class ReportBaseEntity {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "CHAR(16)")
    open var id: String = NanoId.generate()
        protected set

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    open var createdAt: LocalDateTime = LocalDateTime.now()
        protected set
}