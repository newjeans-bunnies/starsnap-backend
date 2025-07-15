package com.photo.server.starsnap.adapter_lambda_s3.handler

import com.amazonaws.services.lambda.runtime.events.S3Event
import com.photo.server.starsnap.exception.global.error.exception.InvalidRoleException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.function.Function

@Component
open class FileUploadHandler(
    private val s3Client: S3Client,
    @Value("\${cloud.aws.s3.output-bucket-name}")
    private val outputBucket: String
) {
    @Bean
    fun fileUploadRouter(): Function<S3Event, String> {
        return Function { s3Event ->
            val record = s3Event.records[0]
            val inputBucket = record.s3.bucket.name
            val key = record.s3.`object`.key

            when {
                key.startsWith("photo/") -> {
                    println("이 파일은 사진입니다.")
                    // 사진 처리 로직
                }
                key.startsWith("video/") -> {
                    println("이 파일은 동영상입니다.")
                    // 동영상 처리 로직
                }
                else -> throw InvalidRoleException
            }

            val extension = key.substringAfterLast('.', "").lowercase()
            println("📄 파일 확장자: $extension")

            val headObject = s3Client.headObject {
                it.bucket(inputBucket)
                it.key(key)
            }

            val contentType = headObject.contentType()
            println("📄 Content-Type: $contentType")

            var keyKey = ""

            when {
                contentType.startsWith("image/") -> {
                    keyKey = "photo"
                }
                contentType.startsWith("video/") -> {
                    keyKey = "video"
                }
            }
            println("📂 파일 분류: $keyKey")

            println("📥 업로드 감지: $inputBucket/$key")

            val processedKey = "$keyKey/$key"
            val content = "처리된 결과 예시".toByteArray()

            // 결과를 output 버킷에 저장
            val putRequest = PutObjectRequest.builder()
                .bucket(outputBucket)
                .key(processedKey)
                .contentType(contentType)
                .build()

            s3Client.putObject(putRequest, RequestBody.fromBytes(content))

            println("✅ 결과 저장: $outputBucket/$processedKey")
            "Processed and stored $key"
        }
    }
}