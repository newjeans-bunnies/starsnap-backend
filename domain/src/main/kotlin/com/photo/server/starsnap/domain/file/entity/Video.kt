package com.photo.server.starsnap.domain.file.entity

import com.photo.server.starsnap.domain.file.entity.base.BaseFile
import com.photo.server.starsnap.domain.file.type.Status
import com.photo.server.starsnap.domain.user.entity.User
import java.time.LocalDateTime

class Video(
    var fileKey: String,
    var source: String,
    var user: User,
    var aiState: Boolean,
    var contentType: String? = null,
    var dateTaken: LocalDateTime? = null,
    var fileSize: Long? = null,
    override val createdAt: LocalDateTime?,
    override var status: Status
) : BaseFile(createdAt, status)
