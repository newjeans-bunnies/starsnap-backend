package com.photo.server.starsnap.domain.star.repository

import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.star.entity.StarGroup

interface StarGroupRepository {
    fun existsByName(name: String): Boolean
    fun findSliceBy(pageable: PageRequest): Slice<StarGroup>?
    fun findByIdOrNull(id: String): StarGroup?
    fun save(starGroup: StarGroup): StarGroup
}