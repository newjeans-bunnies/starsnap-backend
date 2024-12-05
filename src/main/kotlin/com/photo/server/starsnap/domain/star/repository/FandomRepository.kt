package com.photo.server.starsnap.domain.star.repository

import com.photo.server.starsnap.domain.star.entity.FandomEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface FandomRepository : CrudRepository<FandomEntity, String> {
    @Query("SELECT fandom FROM FandomEntity fandom")
    fun findSliceBy(pageable: Pageable): Slice<FandomEntity>?
}