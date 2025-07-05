package com.photo.server.starsnap.adapter_usecase.file.usecase

import com.photo.server.starsnap.config.AwsConfig
import com.photo.server.starsnap.usecase.file.dto.UploadFileRequest
import com.photo.server.starsnap.usecase.file.usecase.AwsUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest

@Service
class AwsUseCaseImpl(
    private val awsConfig: AwsConfig,
    @Value("\${spring.cloud.aws.region.static}")
    private val region: Region,
    @Value("\${spring.cloud.aws.s3.input-bucket}")
    private val bucket: String
): AwsUseCase {

    // Presigned URL 생성
     override fun createPresignedUploadUrl(key: String, metadata: UploadFileRequest): String {
        val presigner = awsConfig.amazonS3Presigner()

        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .metadata(mapOf(
                "ai-state" to metadata.aiState.toString(),
                "date-taken" to metadata.dateTaken.toString(),
                "source" to metadata.source)
            )
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .putObjectRequest(putObjectRequest)
            .signatureDuration(java.time.Duration.ofMinutes(15))
            .build()

        val presignedRequest = presigner.presignPutObject(presignRequest)
        return presignedRequest.url().toString()
    }
}