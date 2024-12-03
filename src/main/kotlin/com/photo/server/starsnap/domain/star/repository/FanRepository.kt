package com.photo.server.starsnap.domain.star.repository

import com.photo.server.starsnap.domain.star.entity.FanEntity
import org.springframework.data.repository.CrudRepository

interface FanRepository: CrudRepository<FanEntity, String> {
}