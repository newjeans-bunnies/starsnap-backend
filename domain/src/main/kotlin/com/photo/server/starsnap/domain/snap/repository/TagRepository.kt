package com.photo.server.starsnap.domain.snap.repository

import com.photo.server.starsnap.domain.snap.entity.Tag

interface TagRepository {
    fun existsByName(name: String): Boolean
    fun findByName(name: String): Tag?
    fun save(tag: Tag): Tag

}