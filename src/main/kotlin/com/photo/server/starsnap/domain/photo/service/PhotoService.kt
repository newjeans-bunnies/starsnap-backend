package com.photo.server.starsnap.domain.photo.service

import com.photo.server.starsnap.domain.photo.dto.PhotoData
import com.photo.server.starsnap.domain.photo.repository.PhotoRepository
import io.viascom.nanoid.NanoId
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
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
    fun sendPhoto(size: Int, page: Int): Slice<PhotoData> {

        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdDate"
            )
        )

        val photoData = photoRepository.findSliceBy(pageRequest) ?: TODO("photoData null")
    }


}