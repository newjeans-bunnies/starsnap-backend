package com.photo.server.starsnap.adapter_usecase.file.usecase

import com.photo.server.starsnap.adapter_infrastructure.file.repository.PhotoRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.file.repository.VideoRepositoryImpl
import com.photo.server.starsnap.domain.file.entity.Photo
import com.photo.server.starsnap.domain.file.entity.Video
import com.photo.server.starsnap.domain.file.type.Status
import com.photo.server.starsnap.domain.snap.entity.Snap
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.exception.file.error.exception.NotFoundPhotoIdException
import com.photo.server.starsnap.exception.file.error.exception.NotFoundVideoIdException
import com.photo.server.starsnap.exception.file.error.exception.PhotoAlreadyLinkedException
import com.photo.server.starsnap.usecase.file.dto.UploadFileRequest
import com.photo.server.starsnap.usecase.file.dto.UploadFileResponse
import com.photo.server.starsnap.usecase.file.usecase.FileUseCase
import io.viascom.nanoid.NanoId
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class FileUseCaseImpl(
    private val awsUseCaseImpl: AwsUseCaseImpl,
    private val photoRepositoryImpl: PhotoRepositoryImpl,
    private val videoRepositoryImpl: VideoRepositoryImpl
) : FileUseCase {

    private val logging = LoggerFactory.getLogger(this.javaClass)


    override fun createPhotoPresidentUrl(request: UploadFileRequest, user: User): UploadFileResponse {
        val path = "photo/${NanoId.generate(32, "_-0123456789abcdefghijklmnopqrstuvwxyz")}"
        val presignedUrl = awsUseCaseImpl.createPresignedUploadUrl(path, request, user)
        return UploadFileResponse(presignedUrl)
    }

    override fun createVideoPresidentUrl(request: UploadFileRequest, user: User): UploadFileResponse {
        val path = "video/${NanoId.generate(32, "_-0123456789abcdefghijklmnopqrstuvwxyz")}"
        val presignedUrl = awsUseCaseImpl.createPresignedUploadUrl(path, request, user)
        return UploadFileResponse(presignedUrl)
    }

    override fun getPhoto(photoId: String): Photo {
        return photoRepositoryImpl.findByIdOrNull(photoId) ?: throw NotFoundPhotoIdException
    }

    override fun getVideo(videoId: String): Video {
        return videoRepositoryImpl.findByIdOrNull(videoId) ?: throw NotFoundVideoIdException
    }

    override fun linkSnapToPhoto(photoId: String, snap: Snap) {
        val photo = photoRepositoryImpl.findByIdOrNull(photoId) ?: throw NotFoundPhotoIdException
        if (photo.status == Status.LINKED) throw PhotoAlreadyLinkedException
        photo.snap = snap
        photo.status = Status.LINKED
        photoRepositoryImpl.save(photo)
    }

    override fun expirePhoto() {
        val cutoffTime = LocalDateTime.now().minusHours(1).toString() // 현재 시간에서 1시간 전
        photoRepositoryImpl.expireOldPendingImages(cutoffTime)
    }
}