package com.photo.server.starsnap.domain.report.entity.base

import io.viascom.nanoid.NanoId
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class SnapReportBaseEntity {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "CHAR(16)")
    var id: String = NanoId.generate()
        protected set

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set
}