package com.photo.server.starsnap.domain.snap.service

import com.photo.server.starsnap.domain.snap.SnapEntity
import com.photo.server.starsnap.domain.snap.dto.SnapDto
import com.photo.server.starsnap.domain.snap.dto.toSnapData
import com.photo.server.starsnap.domain.snap.repository.SnapRepository
import com.photo.server.starsnap.global.utils.type.toType
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.utils.type.isValid
import io.viascom.nanoid.NanoId
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.IOException
import java.time.LocalDateTime
import javax.imageio.ImageIO

@Service
class SnapService(
    private val snapRepository: SnapRepository,
    private val awsS3Service: AwsS3Service,
) {

    fun createSnap(userData: UserEntity, title: String, image: MultipartFile, source: String, dateTaken: String) {
        if (image.contentType.toType().name.isValid()) throw RuntimeException("지원 하지 않는 사진 형식")
        val id = NanoId.generate(16)
        val createdAt = LocalDateTime.now().toString()
        val imageKey = NanoId.generate(16)
        try {
            val bufferedImage: BufferedImage = ImageIO.read(image.inputStream)
            val width = bufferedImage.width
            val height = bufferedImage.height
            awsS3Service.uploadImage(image, imageKey)
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
            throw TODO("존재 하지 않는 snap")
        }

        if (snap.userId.id != userId) throw TODO("권한 없음")

        awsS3Service.deleteImage(snap.imageKey)

        snapRepository.delete(snap)
    }

    fun fixSnap(
        userId: String,
        snapId: String,
        image: MultipartFile?,
        source: String,
        title: String,
        dateTaken: String
    ): SnapDto {
        val snapData = snapRepository.findById(snapId).orElseThrow {
            throw TODO("존재 하지 않는 snap")
        }

        if (snapData.userId.id != userId) throw TODO("권한 없음")
        if (image != null) awsS3Service.fixImage(image, snapData.imageKey)

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

    private fun getExtension(multipartFile: MultipartFile): String? {
        val extension = StringUtils.getFilenameExtension(multipartFile.originalFilename)
        return extension
    }

    private fun String?.isValid(): Boolean {
        return when(this){
            "JPEG", "JPG", "PNG" -> false
            else -> true
        }
    }

}