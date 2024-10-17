package com.photo.server.starsnap.domain.user.service

import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.global.config.AwsS3Config
import com.photo.server.starsnap.global.error.exception.NotExistUserIdException
import com.photo.server.starsnap.global.utils.type.isValid
import com.photo.server.starsnap.global.utils.type.toType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.*
import java.net.URL
import javax.imageio.ImageIO

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
            "profile/$userId.jpg",
            profileImage.inputStream,
            objectMetadata,
        )

        awsS3Config.amazonS3Client().putObject(putObjectRequest)

        userData.profileImageUrl = "profile/$userId.jpg"

        userRepository.save(userData)
    }

    fun addOauthProfileImage(userId: String, profileImageURL: String) = CoroutineScope(Dispatchers.IO).launch {
        val imageUrl = URL(profileImageURL)
        val inputSteam = imageUrl.openStream()
        val bos = ByteArrayOutputStream()

        val image = ImageIO.read(inputSteam)
        ImageIO.write(image, "jpg", bos)


        val multipartFile = CustomMultipartFile(bos.toByteArray(), profileImageURL)
        awsS3Config.amazonS3Client().putObject(createObject(multipartFile, userId))
    }

    private fun createObject(imageData: MultipartFile, userId: String): PutObjectRequest {
        val objectMetadata = ObjectMetadata().apply {
            this.contentType = imageData.contentType
            this.contentLength = imageData.size
        }

        val putObjectRequest = PutObjectRequest(
            bucket,
            "profile/$userId.jpg",
            imageData.inputStream,
            objectMetadata,
        )

        return putObjectRequest
    }
}


class CustomMultipartFile(private val input: ByteArray, private val filename: String) : MultipartFile {
    override fun getInputStream(): InputStream = ByteArrayInputStream(input)

    override fun getName(): String = ""

    override fun getOriginalFilename(): String = filename

    override fun getContentType(): String? = null

    override fun isEmpty(): Boolean = input.isEmpty();

    override fun getSize(): Long = input.size.toLong()

    override fun getBytes(): ByteArray = input

    override fun transferTo(dest: File) {
        FileOutputStream(dest).use { fos ->
            fos.write(input)
        }
    }
}