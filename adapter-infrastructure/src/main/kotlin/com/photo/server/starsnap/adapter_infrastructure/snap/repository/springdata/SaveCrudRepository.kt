package com.photo.server.starsnap.adapter_infrastructure.snap.repository.springdata

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SaveEntity

@Repository
interface SaveCrudRepository: CrudRepository<SaveEntity, String> {
    fun deleteByUserIdAndSnapId(userId: String, snapId: String)
}