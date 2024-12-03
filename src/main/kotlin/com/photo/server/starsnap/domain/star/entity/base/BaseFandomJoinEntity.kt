package com.photo.server.starsnap.domain.star.entity.base

import io.viascom.nanoid.NanoId
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class BaseFandomJoinEntity {
    @Id
    @Column(name = "id", columnDefinition = "CHAR(16)")
    open var id = NanoId.generate(16)
        protected set

    @CreatedDate
    @Column(name = "join_date", nullable = false, updatable = false, columnDefinition = "DATE")
    var joinDate: LocalDateTime? = null
        protected set
}