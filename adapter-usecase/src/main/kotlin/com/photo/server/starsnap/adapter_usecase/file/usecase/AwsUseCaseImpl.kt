package com.photo.server.starsnap.adapter_usecase.file.usecase

import com.photo.server.starsnap.adapter_infrastructure.file.repository.PhotoRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.file.repository.VideoRepositoryImpl
import com.photo.server.starsnap.config.AwsConfig
import com.photo.server.starsnap.domain.file.entity.Photo
import com.photo.server.starsnap.domain.file.entity.Video
import com.photo.server.starsnap.domain.file.type.Status
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.file.dto.UploadFileRequest
import com.photo.server.starsnap.usecase.file.usecase.AwsUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.LocalDateTime

@Service
class AwsUseCaseImpl(
    private val awsConfig: AwsConfig,
    @Value("\${spring.cloud.aws.region.static}")
    private val region: Region,
    @Value("\${spring.cloud.aws.s3.input-bucket}")
    private val bucket: String,

    private val photoRepositoryImpl: PhotoRepositoryImpl,
    private val videoRepositoryImpl: VideoRepositoryImpl
) : AwsUseCase {

    // Presigned URL 생성
    override fun createPresignedUploadUrl(key: String, metadata: UploadFileRequest, user: User): String {
        val presigner = awsConfig.amazonS3Presigner()

        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .metadata(
                mapOf(
                    "ai-state" to metadata.aiState.toString(),
                    "date-taken" to metadata.dateTaken,
                    "source" to metadata.source,
                    "user-id" to user.id
                )
            )
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .putObjectRequest(putObjectRequest)
            .signatureDuration(java.time.Duration.ofMinutes(15))
            .build()

        val presignedRequest = presigner.presignPutObject(presignRequest)
        when{
            key.startsWith("photo/") -> {
                val photo = Photo(
                    fileKey = key,
                    source = metadata.source,
                    aiState = metadata.aiState,
                    dateTaken = parseLocalDateTimeOrNull(metadata.dateTaken),
                    user = user,
                    createdAt = LocalDateTime.now(),
                    status = Status.INIT
                )
                photoRepositoryImpl.save(photo)
            }

            // 동영상 처리 로직
            key.startsWith("video/") -> {
                val video = Video(
                    fileKey = key,
                    source = metadata.source,
                    aiState = metadata.aiState,
                    dateTaken = parseLocalDateTimeOrNull(metadata.dateTaken),
                    createdAt = LocalDateTime.now(),
                    user = user,
                    status = Status.INIT
                )
                videoRepositoryImpl.save(video)
            }
            else -> throw RuntimeException("Unknown key $key")
        }


        return presignedRequest.url().toString()
    }

    private fun parseLocalDateTimeOrNull(input: String?): LocalDateTime? {
        return try {
            if (input.isNullOrBlank()) return null
            LocalDateTime.parse(input)
        } catch (e: Exception) {
            null
        }
    }

}