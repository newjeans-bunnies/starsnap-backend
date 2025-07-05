package com.photo.server.starsnap.domain.file.entity

import com.photo.server.starsnap.domain.file.entity.base.BaseFile
import com.photo.server.starsnap.domain.file.type.PhotoType
import com.photo.server.starsnap.domain.snap.entity.Snap
import java.time.LocalDateTime

data class Photo(
    var fileKey: String,
    var photoType: PhotoType,
    var source: String,
    var aiState: Boolean,
    var dateTaken: LocalDateTime,
    var fileSize: Long,
    var snap: Snap,
    override val createdAt: LocalDateTime?
) : BaseFile(createdAt)