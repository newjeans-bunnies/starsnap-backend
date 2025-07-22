package com.photo.server.starsnap.domain.file.entity

import com.photo.server.starsnap.domain.file.entity.base.BaseFile
import com.photo.server.starsnap.domain.file.type.Status
import com.photo.server.starsnap.domain.snap.entity.Snap
import com.photo.server.starsnap.domain.user.entity.User
import java.time.LocalDateTime

data class Photo(
    var fileKey: String,
    var source: String,
    var aiState: Boolean,
    var dateTaken: LocalDateTime? = null,
    var fileSize: Long? = null,
    var snap: Snap? = null,
    var contentType: String? = null,
    var width: Int? = null,
    var height: Int? = null,
    var user: User,
    override val createdAt: LocalDateTime?,
    override var status: Status
) : BaseFile(createdAt, status)