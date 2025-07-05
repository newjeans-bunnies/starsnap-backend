package com.photo.server.starsnap.adapter_infrastructure.star.repository.springdata

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.star.entity.StarGroupEntity

@Repository
interface StarGroupCrudRepository: CrudRepository<StarGroupEntity, String> {
    fun existsByName(name: String): Boolean

    @Query("SELECT starGroup FROM StarGroupEntity starGroup")
    fun findSliceBy(pageable: Pageable): Slice<StarGroupEntity>?
}