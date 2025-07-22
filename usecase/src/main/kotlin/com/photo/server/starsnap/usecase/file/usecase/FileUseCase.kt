package com.photo.server.starsnap.usecase.file.usecase

import com.photo.server.starsnap.domain.file.entity.Photo
import com.photo.server.starsnap.domain.file.entity.Video
import com.photo.server.starsnap.domain.snap.entity.Snap
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.file.dto.UploadFileRequest
import com.photo.server.starsnap.usecase.file.dto.UploadFileResponse

interface FileUseCase {
    fun createPhotoPresidentUrl(
        request: UploadFileRequest, user: User
    ): UploadFileResponse

    fun createVideoPresidentUrl(
        request: UploadFileRequest, user: User
    ): UploadFileResponse

    fun getPhoto(
        photoId: String
    ): Photo

    fun getVideo(
        videoId: String
    ): Video

    fun linkSnapToPhoto(
        photoId: String,
        snap: Snap
    )

}