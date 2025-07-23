package com.photo.server.starsnap.domain.file.repository

import com.photo.server.starsnap.domain.file.entity.Photo

interface PhotoRepository {
    fun save(photo: Photo): Photo
    fun findByIdOrNull(id: String): Photo?
    fun expireOldPendingImages(cutoffTime: String)
}