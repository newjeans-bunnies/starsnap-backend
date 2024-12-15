package com.photo.server.starsnap.domain.snap.repository

import com.photo.server.starsnap.domain.snap.entity.SaveEntity
import org.springframework.data.repository.CrudRepository

interface SaveRepository: CrudRepository<SaveEntity, String> {
    fun deleteByUserIdAndSnapId(userId: String, snapId: String)
}