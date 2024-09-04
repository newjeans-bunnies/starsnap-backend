package com.photo.server.starsnap.domain.snap.service

import com.photo.server.starsnap.domain.snap.ImageEntity
import com.photo.server.starsnap.domain.snap.dto.CreateImageDto
import com.photo.server.starsnap.domain.snap.dto.ImageDto
import com.photo.server.starsnap.domain.snap.dto.toImageDto
import com.photo.server.starsnap.domain.snap.repository.ImageRepository
import com.photo.server.starsnap.domain.snap.type.toType
import io.viascom.nanoid.NanoId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Service
class ImageService(
    private val imageRepository: ImageRepository,
    private val awsS3Service: AwsS3Service,
) {

    fun createImage(imageData: CreateImageDto): ImageEntity {
        val imageId = NanoId.generate(16)
        val imageType = imageData.image.contentType
        val createdAt = LocalDateTime.now().toString()

        awsS3Service.uploadImage(imageData.image, imageId)

        val imageEntity = ImageEntity(
            id = imageId,
            createdAt = createdAt,
            createdBy = imageData.createdBy,
            size = imageData.image.size,
            source = imageData.source,
            dateTaken = imageData.dateTaken,
            type = imageType.toType()
        )

        imageRepository.save(imageEntity)

        return imageEntity
    }

    fun deleteImage(userId: String, imageId: String) {
        val imageData = imageRepository.findByIdOrNull(imageId) ?: throw TODO("imageId 없는거 애러")
        if (imageData.createdBy.id != userId) throw TODO("권한 없음")

        awsS3Service.deleteImage(imageData.id)
        imageRepository.delete(imageData)
    }

    fun fixImage(image: MultipartFile?, imageId: String, source: String, dateTaken: String): ImageDto {
        val imageData = imageRepository.findByIdOrNull(imageId) ?: throw TODO("not implemented")
        if (image != null) {
            awsS3Service.fixImage(image, imageId)
            imageData.size = image.size
            imageData.type = image.contentType.toType()
        }

        imageData.source = source
        imageData.createdAt = LocalDateTime.now().toString()
        imageData.dateTaken = dateTaken

        imageRepository.save(imageData)

        return imageData.toImageDto()
    }

    fun sendImage(imageIds: List<String>): List<ImageDto> {
        return imageIds.map {
            val imageData = imageRepository.findByIdOrNull(it) ?: throw TODO("존재 하지 않는 imageId")
            imageData.toImageDto()
        }
    }
}