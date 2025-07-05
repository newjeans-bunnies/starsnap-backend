package com.photo.server.starsnap.adapter_infrastructure.star.repository.springdata

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.star.entity.FandomEntity

@Repository
interface FandomCrudRepository : CrudRepository<FandomEntity, String> {
    @Query("SELECT fandom FROM FandomEntity fandom")
    fun findSliceBy(pageable: Pageable): Slice<FandomEntity>?
}