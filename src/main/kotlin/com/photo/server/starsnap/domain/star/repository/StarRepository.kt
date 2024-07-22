package com.photo.server.starsnap.domain.star.repository

import com.photo.server.starsnap.domain.star.StarEntity
import org.springframework.data.jpa.repository.JpaRepository


interface StarRepository: JpaRepository<StarEntity, String>