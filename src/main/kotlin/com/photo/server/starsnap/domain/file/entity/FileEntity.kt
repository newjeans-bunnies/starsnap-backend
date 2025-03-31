package com.photo.server.starsnap.domain.file.entity

import com.photo.server.starsnap.domain.file.type.FileType
import com.photo.server.starsnap.domain.snap.entity.SnapEntity
import io.viascom.nanoid.NanoId
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity(name = "file")
@EntityListeners(AuditingEntityListener::class)
data class FileEntity (
    @Id
    @Column(name = "id", columnDefinition = "CHAR(16)")
    var id: String = NanoId.generate(16),

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    var imageType: FileType, // 사진 확장자

    @Column(name = "source", nullable = false)
    var source: String,

    @Column(name = "ai_state", nullable = false, columnDefinition = "BOOL")
    var aiState: Boolean,

    @Column(name = "file_key", nullable = false)
    var fileKey: String,

    @Column(name = "date_taken", nullable = false, updatable = false, columnDefinition = "DATE")
    var dateTaken: LocalDateTime,

    @Column(name = "file_size", nullable = false)
    var fileSize: Long,

    @CreatedDate
    @Column(name = "create_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    var createAt: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snap_id", nullable = false, updatable = false, columnDefinition = "CHAR(16)")
    val snap: SnapEntity
)