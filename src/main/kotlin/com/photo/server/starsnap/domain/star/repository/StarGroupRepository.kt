package com.photo.server.starsnap.domain.star.repository

import com.photo.server.starsnap.domain.star.entity.StarGroupEntity
import org.springframework.data.repository.CrudRepository

interface StarGroupRepository: CrudRepository<StarGroupEntity, String> {
}