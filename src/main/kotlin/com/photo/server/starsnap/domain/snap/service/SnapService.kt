package com.photo.server.starsnap.domain.snap.service

import com.photo.server.starsnap.domain.snap.SnapEntity
import com.photo.server.starsnap.domain.snap.dto.SnapDto
import com.photo.server.starsnap.domain.snap.dto.toSnapData
import com.photo.server.starsnap.domain.snap.repository.SnapRepository
import com.photo.server.starsnap.domain.snap.type.toType
import com.photo.server.starsnap.domain.user.entity.UserEntity
import io.viascom.nanoid.NanoId
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Service
class SnapService(
    private val snapRepository: SnapRepository,
    private val awsS3Service: AwsS3Service,
) {

    fun createSnap(userData: UserEntity, title: String, image: MultipartFile, source: String, dateTaken: String) {
        val id = NanoId.generate()
        val createdAt = LocalDateTime.now().toString()
        val imageKey = NanoId.generate(16)
        awsS3Service.uploadImage(image, imageKey)

        val snapData = SnapEntity(
            id = id,
            createdAt = createdAt,
            createdBy = userData,
            title = title,
            size = image.size,
            type = image.contentType.toType(),
            source = source,
            dateTaken = dateTaken,
            imageKey = imageKey
        )

        snapRepository.save(snapData)
    }

    fun deleteSnap(userId: String, snapId: String) {
        val snap = snapRepository.findById(snapId).orElseThrow {
            throw TODO("존재 하지 않는 snap")
        }

        if (snap.createdBy.id != userId) throw TODO("권한 없음")

        awsS3Service.deleteImage(snap.imageKey)

        snapRepository.delete(snap)
    }

    fun fixSnap(userId: String, snapId: String, image: MultipartFile?, source: String, title: String, dateTaken: String) : SnapDto {
        val snapData = snapRepository.findById(snapId).orElseThrow {
            throw TODO("존재 하지 않는 snap")
        }

        if(snapData.createdBy.id != userId) throw TODO("권한 없음")
        if(image != null) awsS3Service.fixImage(image, snapData.imageKey)

        with(snapData) {
            this.title = title
            createdAt = LocalDateTime.now().toString()
            this.source = source
            this.dateTaken = dateTaken
        }

        snapRepository.save(snapData)

        return snapData.toSnapData()
    }

    fun sendSnap(size: Int, page: Int): Slice<SnapDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )

        val snapData = snapRepository.findSliceBy(pageRequest) ?: TODO("존재 하지 않는 snap")
        return snapData.map {
            it.toSnapData()
        }
    }
}