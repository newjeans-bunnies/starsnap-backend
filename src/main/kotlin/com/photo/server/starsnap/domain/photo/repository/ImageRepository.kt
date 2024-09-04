package com.photo.server.starsnap.domain.photo.repository

import com.photo.server.starsnap.domain.photo.ImageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository: JpaRepository<ImageEntity, String>