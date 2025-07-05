package com.photo.server.starsnap.domain.file.repository

import com.photo.server.starsnap.domain.file.entity.Video

interface VideoRepository {
    fun save(video: Video): Video
}