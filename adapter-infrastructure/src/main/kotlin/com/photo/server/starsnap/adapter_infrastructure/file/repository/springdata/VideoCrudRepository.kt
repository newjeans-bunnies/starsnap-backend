package com.photo.server.starsnap.adapter_infrastructure.file.repository.springdata

import com.photo.server.starsnap.adapter_infrastructure.file.entity.VideoEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VideoCrudRepository : CrudRepository<VideoEntity, String> {
}