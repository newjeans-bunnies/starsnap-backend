package com.photo.server.starsnap.domain.photo.service

import com.photo.server.starsnap.domain.photo.ImageEntity
import com.photo.server.starsnap.domain.photo.dto.ImageDto
import com.photo.server.starsnap.domain.photo.dto.toImageDto
import com.photo.server.starsnap.domain.photo.repository.ImageRepository
import io.viascom.nanoid.NanoId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Service
class ImageService(
    private val imageRepository: ImageRepository
) {
    fun createImage(username: String, image: MultipartFile): ImageDto {
        val imageData = ImageEntity(
            id = NanoId.generate(16),
            url = "url",
            createdAt = username,
            createdBy = LocalDateTime.now().toString(),
        )
        imageRepository.save(imageData)
        return imageData.toImageDto()
    }

    fun deleteImage(imageId: String) {}

    fun fixImage(image: MultipartFile, imageId: String): ImageDto {}

    fun sendImage(imageIds: List<String>): List<ImageDto> {
        return imageIds.map {
            val imageData = imageRepository.findByIdOrNull(it) ?: throw TODO("존재 하지 않는 imageId")
            imageData.toImageDto()
        }
    }
}