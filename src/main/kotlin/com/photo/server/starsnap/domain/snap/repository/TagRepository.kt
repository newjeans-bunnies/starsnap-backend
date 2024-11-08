package com.photo.server.starsnap.domain.snap.repository

import com.photo.server.starsnap.domain.snap.entity.TagEntity
import org.springframework.data.repository.CrudRepository

interface TagRepository : CrudRepository<TagEntity, String> {
    fun existsByName(name: String): Boolean
    fun findByName(name: String): TagEntity?
}