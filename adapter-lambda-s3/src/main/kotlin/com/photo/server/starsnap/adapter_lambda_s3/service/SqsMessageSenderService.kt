package com.photo.server.starsnap.adapter_lambda_s3.service

import io.awspring.cloud.sqs.operations.SqsTemplate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SqsMessageSenderService(
    @Value("\${cloud.aws.sqs.photo-queue-name}") private val photoQueueName: String,
    @Value("\${cloud.aws.sqs.photo-exception-queue-name}") private val photoExceptionQueueName: String,
    @Value("\${cloud.aws.sqs.video-queue-name}") private val videoQueueName: String,
    @Value("\${cloud.aws.sqs.video-exception-queue-name}") private val videoExceptionQueueName: String,
    private val sqsTemplate: SqsTemplate
) {
    private val logger = LoggerFactory.getLogger(SqsMessageSenderService::class.java)
    fun sendPhotoMessage(message: String) {
        logger.info("Sending photo message: $message") // Debug log
        sqsTemplate.send {
            it.queue(photoQueueName).payload(message)
        }
    }

    fun sendVideoMessage(message: String) {
        logger.info("Sending video message: $message") // Debug log
        sqsTemplate.send {
            it.queue(videoQueueName).payload(message)
        }
    }

    fun photoExceptionMessage(message: String){
        logger.info("애러가 발생 했습니다.")
        sqsTemplate.send {
            it.queue(photoExceptionQueueName).payload(message)
        }
    }

    fun videoExceptionMessage(message: String){
        logger.info("애러가 발생 했습니다.")
        sqsTemplate.send {
            it.queue(videoExceptionQueueName).payload(message)
        }
    }
}