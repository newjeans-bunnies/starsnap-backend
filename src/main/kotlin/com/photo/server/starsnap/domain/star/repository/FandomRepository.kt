package com.photo.server.starsnap.domain.star.repository

import com.photo.server.starsnap.domain.star.entity.FandomEntity
import org.springframework.data.repository.CrudRepository

interface FandomRepository : CrudRepository<FandomEntity, String> {
}