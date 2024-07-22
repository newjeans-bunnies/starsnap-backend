package com.photo.server.starsnap.domain.photo.repository

import com.photo.server.starsnap.domain.photo.PhotoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PhotoRepository: JpaRepository<PhotoEntity, String>