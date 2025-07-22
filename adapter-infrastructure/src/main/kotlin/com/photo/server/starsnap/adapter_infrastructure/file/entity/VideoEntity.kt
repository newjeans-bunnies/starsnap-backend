package com.photo.server.starsnap.adapter_infrastructure.file.entity

import com.photo.server.starsnap.adapter_infrastructure.file.entity.base.BaseFileEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.file.entity.Video
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Table(name = "video")
@EntityListeners(AuditingEntityListener::class)
@Entity
class VideoEntity(
    @Id
    @Column(name = "file_key")
    var fileKey: String,
    @Column(name = "source", nullable = false)
    var source: String,
    @Column(name = "ai_state", nullable = false, columnDefinition = "BOOL")
    var aiState: Boolean,
    @Column(name = "date_taken", nullable = true, updatable = false, columnDefinition = "DATE")
    var dateTaken: LocalDateTime?,
    @Column(name = "file_size", nullable = true)
    var fileSize: Long?,
    @Column(name = "contentType", nullable = true)
    var contentType: String?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    var user: UserEntity,
) : BaseFileEntity() {
    companion object {
        fun fromDomain(video: Video) = VideoEntity(
            fileKey = video.fileKey,
            source = video.source,
            dateTaken = video.dateTaken,
            fileSize = video.fileSize,
            aiState = video.aiState,
            contentType = video.contentType,
            user = UserEntity.fromDomain(video.user),
        )
    }
}
