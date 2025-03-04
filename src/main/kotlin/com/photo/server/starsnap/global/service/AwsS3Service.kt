package com.photo.server.starsnap.global.service

import com.photo.server.starsnap.global.config.AwsS3Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.model.*
import java.io.InputStream
import java.net.URL
import java.time.Duration


@Service
class AwsS3Service(
    private val awsS3Config: AwsS3Config,
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String
) {

    // 파일 다운로드에 필요한 PresignedUrl 생성
    fun getPresignedUrl(s3Path: String): String {
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(bucket)
            .key(s3Path)
            .build()

        val presignedRequest = awsS3Config.amazonS3Presigner().presignGetObject {
            it.signatureDuration(Duration.ofMinutes(10)) // 유효시간 10분
            it.getObjectRequest(getObjectRequest)
        }

        return presignedRequest.url().toString()
    }

    // s3에 파일 존재 여부 확인
    private fun doesObjectExist(s3Path: String): Boolean {
        return try {
            val headObjectRequest = HeadObjectRequest.builder().bucket(bucket).key(s3Path).build()
            val headObjectResponse: HeadObjectResponse = awsS3Config.amazonS3Client().headObject(headObjectRequest)
            return headObjectResponse.sdkHttpResponse().isSuccessful
        } catch (e: NoSuchKeyException) {
            false
        }
    }

    // s3에 파일 삭제
    fun deleteFileTos3(s3Path: String) {
        if (doesObjectExist(s3Path)) {
            val deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(s3Path)
                .build()
            awsS3Config.amazonS3Client().deleteObject(deleteObjectRequest)
        }
    }

    // s3에 파일을 이용하여 파일 업로드
    fun uploadFileToS3(file: MultipartFile, s3Path: String, userId: String): String {
        val request = RequestBody.fromInputStream(file.inputStream, file.size)
        val metadata: MutableMap<String, String> = HashMap()
        metadata["author"] = userId
        metadata["version"] = "1.0.0.0"

        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket) // s3 버킷 이름
            .key(s3Path) // 파일 위치 및 파일 이름
            .acl(ObjectCannedACL.PUBLIC_READ_WRITE) // 권한
            .metadata(metadata)
            .build()

        awsS3Config.amazonS3Client().putObject(putObjectRequest, request)

        return putObjectRequest.key()
    }

    fun uploadUrlToS3(webUrl: String, s3Path: String, userId: String): String {
        val url = URL(webUrl)
        val connection = url.openConnection()

        val stream = urlToInputStream(url)

        val metadata: MutableMap<String, String> = HashMap()
        metadata["author"] = userId
        metadata["version"] = "1.0.0.0"

        val request = RequestBody.fromInputStream(stream, connection.contentLengthLong)

        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket) // s3 버킷 이름
            .key(s3Path) // 파일 위치 및 파일 이름
            .acl(ObjectCannedACL.PUBLIC_READ_WRITE) // 권한
            .metadata(metadata)
            .build()

        awsS3Config.amazonS3Client().putObject(putObjectRequest, request)

        return putObjectRequest.key()
    }

    fun urlToInputStream(url: URL): InputStream {
        return try {
            url.openStream()
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

}