package com.photo.server.starsnap.domain.snap.service

import com.photo.server.starsnap.domain.snap.ImageEntity
import com.photo.server.starsnap.domain.snap.SnapEntity
import com.photo.server.starsnap.domain.snap.dto.SnapDto
import com.photo.server.starsnap.domain.snap.dto.toSnapData
import com.photo.server.starsnap.domain.snap.repository.SnapRepository
import com.photo.server.starsnap.domain.user.UserEntity
import io.viascom.nanoid.NanoId
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SnapService(
    private val snapRepository: SnapRepository
) {

    fun createSnap(userData: UserEntity, title: String, imageData: ImageEntity) {
        val id = NanoId.generate()
        val createdAt = LocalDateTime.now().toString()
        val snapData = SnapEntity(
            id = id,
            createdAt = createdAt,
            createdBy = userData,
            title = title,
            image = imageData
        )
        snapRepository.save(snapData)
    }

    fun deleteSnap(userId: String, id: String) {
        val snap = snapRepository.findById(id).orElseThrow {
            throw TODO("존재 하지 않는 snap")
        }

        if (snap.createdBy.id != userId) throw TODO("권한 없음")

        snapRepository.delete(snap)
    }

    fun fixSnap(id: String, userId: String, title: String) : SnapDto {
        val snapData = snapRepository.findById(id).orElseThrow {
            throw TODO("존재 하지 않는 snap")
        }

        if(snapData.createdBy.id != userId) throw TODO("권한 없음")

        snapData.title = title
        snapData.createdAt = LocalDateTime.now().toString()

        snapRepository.save(snapData)

        return snapData.toSnapData()
    }

    fun sendSnap(size: Int, page: Int): Slice<SnapDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )

        val snapData = snapRepository.findSliceBy(pageRequest) ?: TODO("photoData null")
        return snapData.map {
            it.toSnapData()
        }
    }
}