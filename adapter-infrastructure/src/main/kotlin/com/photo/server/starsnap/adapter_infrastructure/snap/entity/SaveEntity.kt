package com.photo.server.starsnap.adapter_infrastructure.snap.entity

import com.photo.server.starsnap.adapter_infrastructure.snap.entity.base.BaseSaveEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.snap.entity.Save
import java.time.LocalDateTime

@Table(name = "save")
@EntityListeners(AuditingEntityListener::class)
@Entity
class SaveEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, columnDefinition = "CHAR(16)")
    val user: UserEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snap_id", nullable = false, updatable = false, columnDefinition = "CHAR(16)")
    val snap: SnapEntity,
    override var id: String,
    override var saveTime: LocalDateTime?,
) : BaseSaveEntity() {

    companion object {
        fun fromDomain(save: Save) = SaveEntity(
            user = UserEntity.fromDomain(save.user),
            snap = SnapEntity.fromDomain(save.snap),
            id = save.id,
            saveTime = save.saveTime
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