package com.photo.server.starsnap.domain.photo.service

import com.photo.server.starsnap.domain.photo.PhotoEntity
import com.photo.server.starsnap.domain.photo.dto.PhotoData
import com.photo.server.starsnap.domain.photo.repository.PhotoRepository
import io.viascom.nanoid.NanoId
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PhotoService(
    private val photoRepository: PhotoRepository
) {

    fun createPhoto() {
        val id = NanoId.generate()
        val createdAt = LocalDateTime.now().toString()
//        val photoData = PhotoEntity(
//            id = id,
//            createAt = createdAt,
//
//        )
    }

    fun deletePhoto(id: String, userId: String) {}
    fun fixPhoto(id: String, userId: String) {}
    fun sendPhoto(size: Int, page: Int) {}
}