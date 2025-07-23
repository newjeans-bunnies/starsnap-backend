package com.photo.server.starsnap.adapter_infrastructure.file.repository

import com.photo.server.starsnap.adapter_infrastructure.file.FileMapper.toPhoto
import com.photo.server.starsnap.adapter_infrastructure.file.entity.PhotoEntity
import com.photo.server.starsnap.adapter_infrastructure.file.repository.springdata.PhotoCrudRepository
import com.photo.server.starsnap.domain.file.entity.Photo
import com.photo.server.starsnap.domain.file.repository.PhotoRepository
import com.photo.server.starsnap.domain.file.type.Status
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PhotoRepositoryImpl(
    private val photoCrudRepository: PhotoCrudRepository
) : PhotoRepository {
    override fun save(photo: Photo): Photo {
        val photoEntity = PhotoEntity.fromDomain(photo)
        return photoCrudRepository.save(photoEntity).toPhoto()
    }

    override fun findByIdOrNull(id: String): Photo? {
        return photoCrudRepository.findByIdOrNull(id)?.toPhoto()
    }

    override fun expireOldPendingImages(cutoffTime: String) {
        return photoCrudRepository.expireOldPendingImages(cutoffTime, Status.PENDING, Status.EXPIRED)
    }
}