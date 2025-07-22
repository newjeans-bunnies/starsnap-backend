package com.photo.server.starsnap.adapter_infrastructure.file

import com.photo.server.starsnap.adapter_infrastructure.file.entity.PhotoEntity
import com.photo.server.starsnap.adapter_infrastructure.file.entity.VideoEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.SnapMapper.toSnap
import com.photo.server.starsnap.adapter_infrastructure.user.UserMapper.toUser
import com.photo.server.starsnap.domain.file.entity.Photo
import com.photo.server.starsnap.domain.file.entity.Video

object FileMapper {
    fun PhotoEntity.toPhoto(
    ) = Photo(
        fileKey = this.fileKey,
        source = this.source,
        aiState = this.aiState,
        dateTaken = this.dateTaken,
        fileSize = this.fileSize,
        snap = this.snap?.toSnap(),
        createdAt = this.createdAt,
        contentType = this.contentType,
        width = this.width,
        height = this.height,
        status = this.status,
        user = this.user.toUser()
    )

    fun VideoEntity.toVideo() = Video(
        fileKey = this.fileKey,
        source = this.source,
        aiState = this.aiState,
        dateTaken = this.dateTaken,
        fileSize = this.fileSize,
        createdAt = this.createdAt,
        user = this.user.toUser(),
        contentType = this.contentType,
        status = this.status,
    )
}