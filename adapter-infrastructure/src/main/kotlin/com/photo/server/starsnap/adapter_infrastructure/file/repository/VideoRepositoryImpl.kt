package com.photo.server.starsnap.adapter_infrastructure.file.repository

import com.photo.server.starsnap.adapter_infrastructure.file.entity.VideoEntity
import com.photo.server.starsnap.adapter_infrastructure.file.repository.springdata.VideoCrudRepository
import com.photo.server.starsnap.domain.file.entity.Video
import com.photo.server.starsnap.domain.file.repository.VideoRepository
import org.springframework.stereotype.Repository

@Repository
class VideoRepositoryImpl(
    private val videoCrudRepository: VideoCrudRepository
): VideoRepository {
    override fun save(video: Video): Video {
        val videoEntity = VideoEntity.fromDomain(video)
        return videoCrudRepository.save(videoEntity).toDomain()
    }
}