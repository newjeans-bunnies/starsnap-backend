package com.photo.server.starsnap.adapter_infrastructure.star.repository

import com.photo.server.starsnap.adapter_infrastructure.star.entity.FandomEntity
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.star.repository.springdata.FandomCrudRepository
import com.photo.server.starsnap.adapter_infrastructure.extension.toCommonSlice
import com.photo.server.starsnap.adapter_infrastructure.extension.toSpringPageable
import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import com.photo.server.starsnap.domain.star.entity.Fandom
import com.photo.server.starsnap.domain.star.repository.FandomRepository
import org.springframework.data.repository.findByIdOrNull

@Repository
class FandomRepositoryImpl(
    private val fandomCrudRepository: FandomCrudRepository
): FandomRepository {
    override fun findSliceBy(pageable: PageRequest): Slice<Fandom>? {
        return fandomCrudRepository.findSliceBy(pageable.toSpringPageable())?.toCommonSlice()?.map { it.toDomain() }
    }

    override fun findByIdOrNull(id: String): Fandom? {
        return fandomCrudRepository.findByIdOrNull(id)?.toDomain()
    }

    override fun save(fandom: Fandom): Fandom {
        val fandomEntity = FandomEntity.fromDomain(fandom)
        return fandomCrudRepository.save(fandomEntity).toDomain()
    }

    override fun deleteById(id: String) {
        fandomCrudRepository.deleteById(id)
    }
}