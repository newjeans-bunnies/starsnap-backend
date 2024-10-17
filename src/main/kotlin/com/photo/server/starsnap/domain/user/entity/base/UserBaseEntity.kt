package com.photo.server.starsnap.domain.user.entity.base

import io.viascom.nanoid.NanoId
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [EntityListeners::class])
abstract class UserBaseEntity {
    @Id
    @Column(name = "id", columnDefinition = "CHAR(16)", updatable = false)
    open var id = NanoId.generate(16)
        protected set

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    open var createdAt = LocalDateTime.now()
        protected set

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false, columnDefinition = "DATETIME")
    open var modifiedAt = LocalDateTime.now()
        protected set
}