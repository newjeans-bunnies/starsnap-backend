package com.photo.server.starsnap.domain.star.service

import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.photo.server.starsnap.global.config.AwsS3Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import io.viascom.nanoid.NanoId

@Service
class StarImageService(
    private val awsS3Config: AwsS3Config,
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String
) {
    fun uploadImage(image: MultipartFile, type: String): String {
        val imageKey = type + "/" + NanoId.generate()
        val imageType = image.contentType
        val imageSize = image.size

        val objectMetadata = ObjectMetadata().apply {
            this.contentType = imageType
            this.contentLength = imageSize
        }

        val putObjectRequest = PutObjectRequest(
            bucket,
            imageKey,
            image.inputStream,
            objectMetadata,
        )

        awsS3Config.amazonS3Client().putObject(putObjectRequest)
        return imageKey
    }

    fun deleteImage(imageKey: String) {
        if (doesObjectExist(imageKey)) {
            awsS3Config.amazonS3Client().deleteObject(bucket, imageKey)
        }
    }


    fun updateImage(image: MultipartFile, imageKey: String) {
        if (doesObjectExist(imageKey)) {
            val imageSize = image.size

            val objectMetadata = ObjectMetadata().apply {
                this.contentType = image.contentType
                this.contentLength = imageSize
            }

            val putObjectRequest = PutObjectRequest(
                bucket,
                imageKey,
                image.inputStream,
                objectMetadata,
            )

            awsS3Config.amazonS3Client().putObject(putObjectRequest)
        }
    }

    private fun doesObjectExist(imageKey: String) = awsS3Config.amazonS3Client().doesObjectExist(bucket, imageKey)

}