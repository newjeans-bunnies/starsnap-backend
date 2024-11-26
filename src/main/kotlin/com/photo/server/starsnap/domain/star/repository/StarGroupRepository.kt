package com.photo.server.starsnap.domain.star.repository

import com.photo.server.starsnap.domain.star.entity.StarGroupEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface StarGroupRepository: CrudRepository<StarGroupEntity, String> {
    fun existsByName(name: String): Boolean

    @Query("SELECT starGroup FROM StarGroupEntity starGroup")
    fun findSliceBy(pageable: Pageable): Slice<StarGroupEntity>?
}