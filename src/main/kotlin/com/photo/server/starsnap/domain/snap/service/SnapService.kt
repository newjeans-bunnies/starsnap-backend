package com.photo.server.starsnap.domain.snap.service

import com.photo.server.starsnap.domain.snap.entity.SnapEntity
import com.photo.server.starsnap.domain.snap.controller.dto.SnapResponseDto
import com.photo.server.starsnap.domain.snap.entity.TagEntity
import com.photo.server.starsnap.domain.snap.repository.SnapRepository
import com.photo.server.starsnap.domain.snap.repository.TagRepository
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
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.IOException
import java.time.LocalDateTime
import javax.imageio.ImageIO

@Service
class SnapService(
    private val snapRepository: SnapRepository,
    private val tagRepository: TagRepository,
    private val snapAwsS3Service: SnapAwsS3Service
) {

    @Transactional
    fun createSnap(
        userData: UserEntity,
        title: String,
        image: MultipartFile,
        source: String,
        dateTaken: LocalDateTime,
        tags: List<String>
    ) {
        if (image.contentType.toType().name.isValid()) throw RuntimeException("지원 하지 않는 사진 형식")
        val imageKey = NanoId.generate(16)
        try {
            val bufferedImage: BufferedImage = ImageIO.read(image.inputStream)
            snapAwsS3Service.uploadImage(image, imageKey)
            val snapData = SnapEntity(
                title = title,
                imageSize = image.size,
                imageType = image.contentType.toType(),
                source = source,
                dateTaken = dateTaken,
                imageKey = imageKey,
                imageWidth = bufferedImage.width,
                imageHeight = bufferedImage.height,
                user = userData,
                tags = createTags(tags)
            )
            snapRepository.save(snapData)

        } catch (e: IOException) {
            throw e
        }

    }

    fun deleteSnap(userId: String, snapId: String) {
        val snap = snapRepository.findByIdOrNull(snapId) ?: throw RuntimeException("존재 하지 않는 snap")

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
        dateTaken: LocalDateTime,
    ): SnapResponseDto {
        val snapData = snapRepository.findByIdOrNull(snapId) ?: throw RuntimeException("존재 하지 않는 snap")

        if (snapData.userId.id != userId) throw RuntimeException("권한 없음")
        if (image != null) snapAwsS3Service.fixImage(image, snapData.imageKey)

        snapData.title = title
        snapData.source = source
        snapData.dateTaken = dateTaken

        snapRepository.save(snapData)

        return SnapResponseDto(
            createdUser = snapData.userId.toSnapUserDto(),
            snapData = snapData.toSnapDto()
        )
    }

    fun getSnap(size: Int, page: Int, tag: String): Slice<SnapResponseDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val snapData = if (tag.isBlank()) {
            snapRepository.findSliceBy(pageRequest) ?: throw RuntimeException("존재하지 않는 snap")
        } else {
            snapRepository.findSnapTag(pageRequest, tag) ?: throw RuntimeException("존재하지 않는 snap")
        }

        return snapData.map {
            SnapResponseDto(
                createdUser = it.userId.toSnapUserDto(),
                snapData = it.toSnapDto()
            )
        }
    }

    private fun createTags(tags: List<String>): List<TagEntity> {
        return tags.map {
            if (!tagRepository.existsByName(it)) {
                tagRepository.save(TagEntity(name = it))
            } else {
                tagRepository.findByName(it) ?: throw RuntimeException("존재하지 않는 tag")
            }
        }
    }


}