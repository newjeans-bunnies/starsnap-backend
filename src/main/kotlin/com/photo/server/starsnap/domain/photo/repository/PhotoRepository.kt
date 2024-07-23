package com.photo.server.starsnap.domain.photo.repository

import com.photo.server.starsnap.domain.photo.PhotoEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PhotoRepository : JpaRepository<PhotoEntity, String> {
    @Query("SELECT photo FROM PhotoEntity photo")
    fun findSliceBy(pageable: Pageable): Slice<PhotoEntity>?
}