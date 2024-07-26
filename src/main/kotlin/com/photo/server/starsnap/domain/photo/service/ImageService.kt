package com.photo.server.starsnap.domain.photo.service

import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.photo.server.starsnap.domain.photo.ImageEntity
import com.photo.server.starsnap.domain.photo.dto.ImageDto
import com.photo.server.starsnap.domain.photo.dto.toImageDto
import com.photo.server.starsnap.domain.photo.repository.ImageRepository
import com.photo.server.starsnap.domain.photo.type.toType
import com.photo.server.starsnap.global.config.AwsS3Config
import io.viascom.nanoid.NanoId
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Service
class ImageService(
    private val imageRepository: ImageRepository,
    private val awsS3Config: AwsS3Config,
    @Value("\${cloud.aws.s3.bucket}") private val bucket: String
) {

    fun createImage(username: String, image: MultipartFile, photoId: String, source: String): ImageDto {

        val imageId = NanoId.generate(16)
        val imageType = image.contentType
        val imageSize = image.size
        val url = "${photoId}/${imageId}"
        val createdAt = LocalDateTime.now().toString()
        val type = imageType.toType()

        val objectMetadata = ObjectMetadata().apply {
            this.contentType = imageType
            this.contentLength = imageSize
        }

        val putObjectRequest = PutObjectRequest(
            bucket,
            url,
            image.inputStream,
            objectMetadata,
        )

        awsS3Config.amazonS3Client().putObject(putObjectRequest)

        val imageData = ImageEntity(
            id = imageId,
            url = url,
            createdAt = createdAt,
            createdBy = username,
            size = imageSize,
            source = source,
            type = type
        )

        imageRepository.save(imageData)

        return imageData.toImageDto()
    }

    fun deleteImage(imageId: String) {
        val imageData = imageRepository.findByIdOrNull(imageId) ?: throw TODO("imageId없는거 애러")
        val isObjectExist = awsS3Config.amazonS3Client().doesObjectExist(bucket, imageData.url)
        if (isObjectExist) {
            awsS3Config.amazonS3Client().deleteObject(bucket, imageData.url)
        }
        imageRepository.delete(imageData)
    }

    fun fixImage(image: MultipartFile, imageId: String, source: String): ImageDto {
        val imageData = imageRepository.findByIdOrNull(imageId) ?: throw TODO("not implemented")

        val isObjectExist = awsS3Config.amazonS3Client().doesObjectExist(bucket, imageData.url)
        if (isObjectExist) {
            val imageType = image.contentType
            val imageSize = image.size

            val objectMetadata = ObjectMetadata().apply {
                this.contentType = imageType
                this.contentLength = imageSize
            }

            val putObjectRequest = PutObjectRequest(
                bucket,
                imageData.url,
                image.inputStream,
                objectMetadata,
            )
            awsS3Config.amazonS3Client().putObject(putObjectRequest)
        }

        with(imageData) {
            this.source = source
            size = image.size
            type = image.contentType.toType()
            createdAt = LocalDateTime.now().toString()
        }
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