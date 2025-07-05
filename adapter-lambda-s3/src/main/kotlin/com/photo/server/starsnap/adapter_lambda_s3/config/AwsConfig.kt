package com.photo.server.starsnap.adapter_lambda_s3.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.lambda.LambdaClient
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
open class AwsConfig {
    @Bean
    open fun amazonS3Client(): S3Client = S3Client.builder().region(Region.AP_NORTHEAST_2).build()

    @Bean
    open fun amazonS3AsyncClient(): S3AsyncClient = S3AsyncClient.builder().region(Region.AP_NORTHEAST_2).build()

    @Bean
    open fun amazonS3Presigner(): S3Presigner = S3Presigner.builder()
        .region(Region.AP_NORTHEAST_2) // AWS 리전 (서울)
        .credentialsProvider(DefaultCredentialsProvider.create())
        .build()

    @Bean
    open fun lambdaClient(): LambdaClient = LambdaClient.builder()
        .region(Region.AP_NORTHEAST_2) // 사용 중인 리전을 지정하세요.
        .build()
}