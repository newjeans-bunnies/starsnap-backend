package com.photo.server.starsnap.adapter_infrastructure.file.repository.springdata

import com.photo.server.starsnap.adapter_infrastructure.file.entity.PhotoEntity
import com.photo.server.starsnap.domain.file.type.Status
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface PhotoCrudRepository : CrudRepository<PhotoEntity, String> {
    @Modifying
    @Transactional
    @Query(
        "UPDATE PhotoEntity photo SET photo.status = :newStatus WHERE photo.status = :oldStatus AND photo.createdAt < :cutoffTime"
    )
    fun expireOldPendingImages(
        cutoffTime: String,
        oldStatus: Status,
        newStatus: Status
    )
}