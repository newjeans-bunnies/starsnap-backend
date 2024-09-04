package com.photo.server.starsnap.domain.photo.service

import com.photo.server.starsnap.domain.photo.repository.ImageRepository
import org.springframework.stereotype.Service

@Service
class ImageService(
    private val imageRepository: ImageRepository
) {
}