package com.photo.server.starsnap.global.config

import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
class AwsS3Config(
    @Value("\${cloud.aws.credentials.access-key}")
    private val accessKey: String,
    @Value("\${cloud.aws.credentials.secret-key}")
    private val secretKey: String,
    @Value("\${cloud.aws.region.static}")
    private val region: Region
) {
    @Bean
    fun amazonS3Client(): S3Client = S3Client.builder().region(region).build()

    @Bean
    fun amazonS3Presigner(): S3Presigner = S3Presigner.builder()
        .region(region) // AWS 리전 (서울)
        .credentialsProvider(DefaultCredentialsProvider.create())
        .build()

}