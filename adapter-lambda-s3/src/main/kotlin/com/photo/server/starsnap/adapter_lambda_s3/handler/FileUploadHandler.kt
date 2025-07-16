package com.photo.server.starsnap.adapter_lambda_s3.handler

import com.amazonaws.services.lambda.runtime.events.S3Event
import com.photo.server.starsnap.exception.global.error.exception.InvalidRoleException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.function.Function

@Component
open class FileUploadHandler(
    private val s3Client: S3Client,
    @Value("\${cloud.aws.s3.output-bucket-name}")
    private val outputBucket: String,
) {
    @Bean
    fun fileUploadRouter(): Function<S3Event, String> {
        return Function { s3Event ->
            val record = s3Event.records[0]
            val inputBucket = record.s3.bucket.name
            println("Input Bucket: $inputBucket")
            val key = URLDecoder.decode(record.s3.`object`.key, StandardCharsets.UTF_8.name())
            println("Key: $key")

            val headObject = s3Client.headObject {
                it.bucket(inputBucket)
                it.key(key)
            }

            val s3Object = s3Client.getObject {
                it.bucket(inputBucket)
                it.key(key)
            }

            val file = s3Object.readAllBytes()


            val metadata = headObject.metadata()
            val contentType = headObject.contentType()

            when {
                // 사진 처리 로직
                key.startsWith("photo/") -> {
                    println("이 파일은 사진입니다.")
                    photoUpload(key, contentType, file, metadata)
                }

                // 동영상 처리 로직
                key.startsWith("video/") -> {
                    println("이 파일은 동영상입니다.")
                    videoUpload(key, contentType, file, metadata)
                }

                else -> throw InvalidRoleException
            }
        }
    }

    fun videoUpload(key: String, contentType: String, file: ByteArray, metadata: Map<String,String>): String {
        // 결과를 output 버킷에 저장
        val putRequest = PutObjectRequest.builder()
            .bucket(outputBucket)
            .key(key)
            .contentType(contentType)
            .metadata(metadata)
            .build()

        s3Client.putObject(putRequest, RequestBody.fromBytes(file))
        println("Video uploaded successfully to $outputBucket/$key")
        return key
    }

    fun photoUpload(key: String, contentType: String, file: ByteArray, metadata: Map<String,String>): String {
        // 결과를 output 버킷에 저장
        val putRequest = PutObjectRequest.builder()
            .bucket(outputBucket)
            .key(key)
            .contentType(contentType)
            .metadata(metadata)
            .build()

        s3Client.putObject(putRequest, RequestBody.fromBytes(file))
        println("Photo uploaded successfully to $outputBucket/$key")

        return key
    }
}