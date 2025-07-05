package com.photo.server.starsnap.adapter_infrastructure.file.entity

import com.photo.server.starsnap.adapter_infrastructure.file.entity.base.BaseFileEntity
import com.photo.server.starsnap.domain.file.entity.Video
import com.photo.server.starsnap.domain.file.type.VideoType
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
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    var videoType: VideoType,
    @Column(name = "source", nullable = false)
    var source: String,
    @Column(name = "ai_state", nullable = false, columnDefinition = "BOOL")
    var aiState: Boolean,
    @Column(name = "date_taken", nullable = true, updatable = false, columnDefinition = "DATE")
    var dateTaken: LocalDateTime?,
    @Column(name = "file_size", nullable = false)
    var fileSize: Long
) : BaseFileEntity(){
    fun toDomain(): Video = Video(
        fileKey = this.fileKey,
        videoType = this.videoType,
        source = this.source,
        aiState = this.aiState,
        dateTaken = this.dateTaken,
        fileSize = this.fileSize,
        createdAt = super.createdAt,
    )
    companion object {
        fun fromDomain(video: Video) = VideoEntity(
            fileKey = video.fileKey,
            videoType = video.videoType,
            source = video.source,
            dateTaken = video.dateTaken,
            fileSize = video.fileSize,
            aiState = video.aiState
        )
    }
}
