package com.photo.server.starsnap.adapter_infrastructure.file.entity

import com.photo.server.starsnap.adapter_infrastructure.file.entity.base.BaseFileEntity
import com.photo.server.starsnap.domain.file.entity.Photo
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SnapEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.file.type.Status
import java.time.LocalDateTime

@Table(name = "photo")
@EntityListeners(AuditingEntityListener::class)
@Entity
class PhotoEntity(
    @Id @Column(name = "file_key")
    var fileKey: String,
    @Column(name = "source", nullable = false)
    var source: String,
    @Column(name = "ai_state", nullable = false, columnDefinition = "BOOL")
    var aiState: Boolean,
    @Column(name = "date_taken", nullable = true, columnDefinition = "DATE")
    var dateTaken: LocalDateTime?,
    @Column(name = "file_size", nullable = true)
    var fileSize: Long?,
    @Column(name = "width", nullable = true)
    var width: Int?,
    @Column(name = "height", nullable = true)
    var height: Int?,
    @Column(name = "content_type", nullable = true)
    var contentType: String?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snap_id", nullable = true, updatable = false)
    var snap: SnapEntity?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    var user: UserEntity,
    override var createdAt: LocalDateTime?,
    override var status: Status,
) : BaseFileEntity() {


    companion object {
        fun fromDomain(photo: Photo) = PhotoEntity(
            fileKey = photo.fileKey,
            source = photo.source,
            aiState = photo.aiState,
            dateTaken = photo.dateTaken,
            fileSize = photo.fileSize,
            width = photo.width,
            height = photo.height,
            contentType = photo.contentType,
            snap = photo.snap?.let { SnapEntity.fromDomain(it) },
            user = UserEntity.fromDomain(photo.user),
            status = photo.status,
            createdAt = photo.createdAt
        )
    }
}