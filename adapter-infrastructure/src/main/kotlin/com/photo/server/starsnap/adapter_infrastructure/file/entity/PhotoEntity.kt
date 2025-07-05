package com.photo.server.starsnap.adapter_infrastructure.file.entity

import com.photo.server.starsnap.adapter_infrastructure.file.entity.base.BaseFileEntity
import com.photo.server.starsnap.domain.file.entity.Photo
import com.photo.server.starsnap.domain.file.type.PhotoType
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SnapEntity
import java.time.LocalDateTime

@Table(name = "photo")
@EntityListeners(AuditingEntityListener::class)
@Entity
class PhotoEntity(
    @Id @Column(name = "file_key")
    var fileKey: String,
    // 사진 확장자
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    var photoType: PhotoType,
    @Column(name = "source", nullable = false)
    var source: String,
    @Column(name = "ai_state", nullable = false, columnDefinition = "BOOL")
    var aiState: Boolean,
    @Column(
        name = "date_taken", nullable = false, columnDefinition = "DATE"
    ) var dateTaken: LocalDateTime,
    @Column(name = "file_size", nullable = false)
    var fileSize: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snap_id", nullable = false, updatable = false)
    var snap: SnapEntity
) : BaseFileEntity(){
    fun toDomain() = Photo(
        fileKey = this.fileKey,
        photoType = this.photoType,
        source = this.source,
        aiState = this.aiState,
        dateTaken = this.dateTaken,
        fileSize = this.fileSize,
        snap = this.snap.toDomain(),
        createdAt = super.createdAt
    )
    companion object {
        fun fromDomain(photo: Photo) = PhotoEntity(
            fileKey = photo.fileKey,
            photoType = photo.photoType,
            source = photo.source,
            aiState = photo.aiState,
            dateTaken = photo.dateTaken,
            fileSize = photo.fileSize,
            snap = SnapEntity.fromDomain(photo.snap)
        )
    }
}