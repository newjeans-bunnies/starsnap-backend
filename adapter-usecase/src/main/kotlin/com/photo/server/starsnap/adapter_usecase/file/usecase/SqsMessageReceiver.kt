package com.photo.server.starsnap.adapter_usecase.file.usecase

import com.fasterxml.jackson.databind.ObjectMapper
import com.photo.server.starsnap.adapter_infrastructure.file.repository.PhotoRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.file.repository.VideoRepositoryImpl
import com.photo.server.starsnap.domain.file.dto.PhotoMetaDateDto
import com.photo.server.starsnap.domain.file.dto.VideoMetaDateDto
import com.photo.server.starsnap.domain.file.type.Status
import com.photo.server.starsnap.exception.file.error.exception.NotFoundPhotoIdException
import com.photo.server.starsnap.exception.file.error.exception.NotFoundVideoIdException
import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sqs.SqsClient

@Component
class SqsMessageReceiver(
    private val sqsClient: SqsClient,
    @Value("\${spring.cloud.aws.sqs.queue-url}") private val queueUrl: String,
    private val jacksonObjectMapper: ObjectMapper,

    private val photoRepositoryImpl: PhotoRepositoryImpl,
    private val videoRepositoryImpl: VideoRepositoryImpl,
) {

    @SqsListener("starsnap_photo.fifo")
    fun createPhoto(@Payload payload: String) {
        val dto = jacksonObjectMapper.readValue(payload, PhotoMetaDateDto::class.java)
        val photo = photoRepositoryImpl.findByIdOrNull(dto.fileKey) ?: throw NotFoundPhotoIdException

        photo.status = Status.PENDING
        photoRepositoryImpl.save(photo)
        println(payload)
    }

    @SqsListener("starsnap_video.fifo")
    fun createVideo(@Payload payload: String) {
        val dto = jacksonObjectMapper.readValue(payload, VideoMetaDateDto::class.java)
        val video = videoRepositoryImpl.findByIdOrNull(dto.fileKey) ?: throw NotFoundVideoIdException

        video.status = Status.PENDING
        videoRepositoryImpl.save(video)

        println(payload)
    }

}