package com.photo.server.starsnap.domain.file.entity

import com.photo.server.starsnap.domain.file.entity.base.BaseFile
import com.photo.server.starsnap.domain.file.type.VideoType
import java.time.LocalDateTime

class Video(
    var fileKey: String,
    var videoType: VideoType,
    var source: String,
    var aiState: Boolean,
    var dateTaken: LocalDateTime?,
    var fileSize: Long,
    override val createdAt: LocalDateTime?
) : BaseFile(createdAt)
