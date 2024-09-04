package com.photo.server.starsnap.domain.snap.repository

import com.photo.server.starsnap.domain.snap.ImageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository: JpaRepository<ImageEntity, String>