package com.photo.server.starsnap.adapter_infrastructure.snap.repository

import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SaveEntity
import com.photo.server.starsnap.domain.snap.entity.Save
import com.photo.server.starsnap.domain.snap.repository.SaveRepository
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.springdata.SaveCrudRepository

@Repository
class SaveRepositoryImpl(
    private val saveCrudRepository: SaveCrudRepository
): SaveRepository {
    override fun deleteByUserIdAndSnapId(userId: String, snapId: String) {
        return saveCrudRepository.deleteByUserIdAndSnapId(userId, snapId)
    }

    override fun save(save: Save): Save {
        return saveCrudRepository.save(SaveEntity.fromDomain(save)).toDomain()
    }
}