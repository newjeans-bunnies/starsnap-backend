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
import org.slf4j.LoggerFactory
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
    private val logging = LoggerFactory.getLogger(this.javaClass)


    @SqsListener("starsnap_photo.fifo")
    fun createPhoto(@Payload payload: String) {
        val dto = jacksonObjectMapper.readValue(payload, PhotoMetaDateDto::class.java)
        val photo = photoRepositoryImpl.findByIdOrNull(dto.fileKey) ?: throw NotFoundPhotoIdException

        photo.status = Status.PENDING
        photo.width = dto.width.toInt()
        photo.height = dto.height.toInt()
        photo.fileSize = dto.fileSize.toLong()
        photo.contentType = dto.contentType
        photo.status = Status.PENDING
        photoRepositoryImpl.save(photo)
        logging.info(payload)
    }

    @SqsListener("starsnap_video.fifo")
    fun createVideo(@Payload payload: String) {
        val dto = jacksonObjectMapper.readValue(payload, VideoMetaDateDto::class.java)
        val video = videoRepositoryImpl.findByIdOrNull(dto.fileKey) ?: throw NotFoundVideoIdException

        video.status = Status.PENDING
        video.contentType = dto.contentType
        videoRepositoryImpl.save(video)

        logging.info(payload)
    }

}