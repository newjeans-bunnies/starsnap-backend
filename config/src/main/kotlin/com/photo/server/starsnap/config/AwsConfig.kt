package com.photo.server.starsnap.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
class AwsConfig(
    @Value("\${spring.cloud.aws.region.static}")
    private val region: Region,
    @Value("\${spring.cloud.aws.s3.input-bucket}")
    private val bucket: String
) {
    @Bean
    fun amazonS3Presigner(): S3Presigner = S3Presigner.builder()
        .region(region) // AWS 리전 (서울)
        .credentialsProvider(DefaultCredentialsProvider.create())
        .build()
}