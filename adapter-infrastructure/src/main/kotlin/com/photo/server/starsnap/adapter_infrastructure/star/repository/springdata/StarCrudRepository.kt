package com.photo.server.starsnap.adapter_infrastructure.star.repository.springdata

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.star.entity.StarEntity

@Repository
interface StarCrudRepository: CrudRepository<StarEntity, String> {
    fun existsByName(name: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}