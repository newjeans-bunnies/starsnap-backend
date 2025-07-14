package com.photo.server.starsnap.adapter_infrastructure.snap.entity

import com.photo.server.starsnap.adapter_infrastructure.snap.entity.base.BaseSaveEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.snap.entity.Save

@Table(name = "save")
@EntityListeners(AuditingEntityListener::class)
@Entity
class SaveEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, columnDefinition = "CHAR(16)")
    val user: UserEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snap_id", nullable = false, updatable = false, columnDefinition = "CHAR(16)")
    val snap: SnapEntity
) : BaseSaveEntity() {

    companion object {
        fun fromDomain(save: Save) = SaveEntity(
            user = UserEntity.fromDomain(save.user),
            snap = SnapEntity.fromDomain(save.snap)
        )
    }

    @PostRemove
    fun removeFollow() {
        user.saveCount -= 1
    }

    @PrePersist
    fun createdFollow() {
        user.saveCount += 1
    }
}