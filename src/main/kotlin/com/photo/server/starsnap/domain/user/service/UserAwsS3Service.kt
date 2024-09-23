package com.photo.server.starsnap.domain.user.service

import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.global.config.AwsS3Config
import com.photo.server.starsnap.global.error.exception.NotExistUserIdException
import com.photo.server.starsnap.global.utils.type.isValid
import com.photo.server.starsnap.global.utils.type.toType
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UserAwsS3Service(
    private val userRepository: UserRepository,
    private val awsS3Config: AwsS3Config,
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String
) {
    fun changeProfileImage(userId: String, profileImage: MultipartFile) {

        val userData = userRepository.findByIdOrNull(userId) ?: throw NotExistUserIdException

        if (profileImage.contentType.toType().name.isValid()) throw RuntimeException("지원 하지 않는 사진 형식")

        val objectMetadata = ObjectMetadata().apply {
            this.contentType = profileImage.contentType
            this.contentLength = profileImage.size
        }

        val putObjectRequest = PutObjectRequest(
            bucket,
            "profile/$userId",
            profileImage.inputStream,
            objectMetadata,
        )

        awsS3Config.amazonS3Client().putObject(putObjectRequest)

        userData.profileImageUrl = "profile/$userId"

        userRepository.save(userData)
    }
}