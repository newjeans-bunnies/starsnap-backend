package com.photo.server.starsnap.domain.snap.service

import com.photo.server.starsnap.domain.snap.SnapEntity
import com.photo.server.starsnap.domain.snap.controller.dto.SnapResponseDto
import com.photo.server.starsnap.domain.snap.repository.SnapRepository
import com.photo.server.starsnap.global.utils.type.toType
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.dto.toSnapDto
import com.photo.server.starsnap.global.dto.toSnapUserDto
import com.photo.server.starsnap.global.utils.type.isValid
import io.viascom.nanoid.NanoId
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.IOException
import java.time.LocalDateTime
import javax.imageio.ImageIO

@Service
class SnapService(
    private val snapRepository: SnapRepository,
    private val snapAwsS3Service: SnapAwsS3Service
) {

    @Transactional
    fun createSnap(userData: UserEntity, title: String, image: MultipartFile, source: String, dateTaken: String) {
        if (image.contentType.toType().name.isValid()) throw RuntimeException("지원 하지 않는 사진 형식")
        val id = NanoId.generate(16)
        val createdAt = LocalDateTime.now().toString()
        val imageKey = NanoId.generate(16)
        try {
            val bufferedImage: BufferedImage = ImageIO.read(image.inputStream)
            val width = bufferedImage.width
            val height = bufferedImage.height
            snapAwsS3Service.uploadImage(image, imageKey)
            val snapData = SnapEntity(
                id = id,
                createdAt = createdAt,
                userId = userData,
                title = title,
                size = image.size,
                type = image.contentType.toType(),
                source = source,
                dateTaken = dateTaken,
                imageKey = imageKey,
                imageWidth = width,
                imageHeight = height
            )
            snapRepository.save(snapData)

        } catch (e: IOException) {
            throw e
        }

    }

    fun deleteSnap(userId: String, snapId: String) {
        val snap = snapRepository.findById(snapId).orElseThrow {
            throw RuntimeException("존재 하지 않는 snap")
        }

        if (snap.userId.id != userId) throw RuntimeException("권한 없음")

        snapAwsS3Service.deleteImage(snap.imageKey)

        snapRepository.delete(snap)
    }

    fun fixSnap(
        userId: String,
        snapId: String,
        image: MultipartFile?,
        source: String,
        title: String,
        dateTaken: String
    ): SnapResponseDto {
        val snapData = snapRepository.findById(snapId).orElseThrow {
            throw RuntimeException("존재 하지 않는 snap")
        }

        if (snapData.userId.id != userId) throw RuntimeException("권한 없음")
        if (image != null) snapAwsS3Service.fixImage(image, snapData.imageKey)

        with(snapData) {
            this.title = title
            createdAt = LocalDateTime.now().toString()
            this.source = source
            this.dateTaken = dateTaken
        }

        snapRepository.save(snapData)

        return SnapResponseDto(
            createdUser = snapData.userId.toSnapUserDto(),
            snapData = snapData.toSnapDto()
        )
    }

    fun getSnap(size: Int, page: Int): Slice<SnapResponseDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )

        val snapData = snapRepository.findSliceBy(pageRequest) ?: throw RuntimeException("존재 하지 않는 snap")

        return snapData.map {
            SnapResponseDto(
                createdUser = it.userId.toSnapUserDto(),
                snapData = it.toSnapDto()
            )
        }
    }
}