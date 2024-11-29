package com.photo.server.starsnap.domain.star.repository

import com.photo.server.starsnap.domain.star.entity.StarEntity
import org.springframework.data.repository.CrudRepository

interface StarRepository: CrudRepository<StarEntity, String> {
    fun existsByName(name: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}