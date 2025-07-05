package com.photo.server.starsnap.adapter_infrastructure.snap.repository.springdata

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.TagEntity

@Repository
interface TagCrudRepository : CrudRepository<TagEntity, String> {
    fun existsByName(name: String): Boolean
    fun findByName(name: String): TagEntity?
}