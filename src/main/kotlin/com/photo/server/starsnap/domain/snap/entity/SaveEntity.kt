package com.photo.server.starsnap.domain.snap.entity

import com.photo.server.starsnap.domain.snap.entity.base.BaseSaveEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Table(name = "save")
@EntityListeners(AuditingEntityListener::class)
@Entity
class SaveEntity(
    user: UserEntity,
    snap: SnapEntity
): BaseSaveEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, columnDefinition = "CHAR(16)")
    val user: UserEntity = user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snap_id", nullable = false, updatable = false, columnDefinition = "CHAR(16)")
    val snap: SnapEntity = snap

    @PostRemove
    fun removeFollow() {
        user.saveCount -= 1
    }

    @PrePersist
    fun createdFollow() {
        user.saveCount += 1
    }
}