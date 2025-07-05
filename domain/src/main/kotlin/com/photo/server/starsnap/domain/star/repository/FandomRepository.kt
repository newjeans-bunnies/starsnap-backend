package com.photo.server.starsnap.domain.star.repository

import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.star.entity.Fandom

interface FandomRepository {
    fun findSliceBy(pageable: PageRequest): Slice<Fandom>?
    fun findByIdOrNull(idn: String): Fandom?
    fun save(fandom: Fandom): Fandom
    fun deleteById(id: String)
}