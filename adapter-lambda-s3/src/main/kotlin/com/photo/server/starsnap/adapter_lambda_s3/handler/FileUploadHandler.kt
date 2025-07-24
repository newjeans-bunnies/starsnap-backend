package com.photo.server.starsnap.adapter_lambda_s3.handler

import com.amazonaws.services.lambda.runtime.events.S3Event
import com.fasterxml.jackson.databind.ObjectMapper
import com.photo.server.starsnap.adapter_lambda_s3.service.SqsMessageSenderService
import com.photo.server.starsnap.domain.file.dto.PhotoMetaDateDto
import com.photo.server.starsnap.domain.file.dto.VideoMetaDateDto
import com.photo.server.starsnap.exception.file.error.exception.UnsupportedFileTypeException
import com.photo.server.starsnap.exception.global.error.exception.InvalidRoleException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.function.Function
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO


@Component
open class FileUploadHandler(
    private val s3Client: S3Client,
    @Value("\${cloud.aws.s3.output-bucket-name}")
    private val outputBucket: String,
    private val sqsMessageSenderService: SqsMessageSenderService,
    private val jacksonObjectMapper: ObjectMapper
) {
    private val logging = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun fileUploadRouter(): Function<S3Event, String> {
        return Function { s3Event ->
            val record = s3Event.records[0]
            val inputBucket = record.s3.bucket.name
            logging.info("Input Bucket: $inputBucket")
            val key = URLDecoder.decode(record.s3.`object`.key, StandardCharsets.UTF_8.name())
            logging.info("Key: $key")

            val headObject = s3Client.headObject {
                it.bucket(inputBucket)
                it.key(key)
            }

            val s3Object = s3Client.getObject {
                it.bucket(inputBucket)
                it.key(key)
            }

            val file = s3Object.readAllBytes()
            val contentType = headObject.contentType()

            // 파일 메타데이터
            val metadata = headObject.metadata()
            val filedata = metadata.toMutableMap()
            filedata["file-size"] = file.size.toString()
            filedata["Content-Type"] = contentType


            when {
                // 사진 처리 로직
                key.startsWith("photo/") -> {
                    logging.info("이 파일은 사진입니다.")
                    photoUpload(key, contentType, file, filedata)
                }

                // 동영상 처리 로직
                key.startsWith("video/") -> {
                    logging.info("이 파일은 동영상입니다.")
                    videoUpload(key, contentType, file, filedata)
                }

                else -> throw InvalidRoleException
            }
        }
    }

    // 영상 업로드
    fun videoUpload(key: String, contentType: String, file: ByteArray, metadata: Map<String,String>): String {
        try {
            val filedata = metadata.toMutableMap()
            val putRequest = PutObjectRequest.builder()
                .bucket(outputBucket)
                .key(key)
                .contentType(contentType)
                .metadata(metadata)
                .build()

            val videoMetaDateDto = VideoMetaDateDto(
                fileKey = key,
                fileSize = filedata["file-size"] ?: "0",
                contentType = contentType
            )
            val jsonData = jacksonObjectMapper.writeValueAsString(videoMetaDateDto)

            s3Client.putObject(putRequest, RequestBody.fromBytes(file))
            sqsMessageSenderService.sendVideoMessage(jsonData)
            logging.info("Video uploaded successfully to $outputBucket/$key")


            return key
        } catch (e: Exception) {
            logging.error("Error processing video upload: ${e.message}", e)
            sqsMessageSenderService.videoExceptionMessage(e.message.toString())
            return ""
        }

    }

    // 사진 업로드
    fun photoUpload(key: String, contentType: String, file: ByteArray, metadata: Map<String,String>): String {
        try {
            val filedata = metadata.toMutableMap()

            val inputStream = ByteArrayInputStream(file)
            val image = ImageIO.read(inputStream) ?: throw UnsupportedFileTypeException

            filedata["width"] = image.width.toString()
            filedata["height"] = image.width.toString()

            val photoMetaDateDto = PhotoMetaDateDto(
                fileKey = key,
                snapId = filedata["snap-id"] ?: "",
                fileSize = filedata["file-size"] ?: "0",
                width = image.width.toString(),
                height = image.height.toString(),
                contentType = contentType
            )

            val jsonData = jacksonObjectMapper.writeValueAsString(photoMetaDateDto)

            logging.info(photoMetaDateDto.toString())

            // 결과를 output 버킷에 저장
            val putRequest = PutObjectRequest.builder()
                .bucket(outputBucket)
                .key(key)
                .contentType(contentType)
                .metadata(filedata)
                .build()

            s3Client.putObject(putRequest, RequestBody.fromBytes(file))
            sqsMessageSenderService.sendPhotoMessage(jsonData)
            logging.info("Photo uploaded successfully to $outputBucket/$key")

            return key
        } catch (e: Exception) {
            logging.error("Error processing photo upload: ${e.message}", e)
            sqsMessageSenderService.photoExceptionMessage(e.message.toString())
            return ""
        }
    }
}