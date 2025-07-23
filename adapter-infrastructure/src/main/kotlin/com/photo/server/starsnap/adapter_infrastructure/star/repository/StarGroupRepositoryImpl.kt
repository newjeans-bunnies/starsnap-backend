package com.photo.server.starsnap.adapter_infrastructure.star.repository

import com.photo.server.starsnap.adapter_infrastructure.star.entity.StarGroupEntity
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.star.repository.springdata.StarGroupCrudRepository
import com.photo.server.starsnap.adapter_infrastructure.global.extension.toCommonSlice
import com.photo.server.starsnap.adapter_infrastructure.global.extension.toSpringPageable
import com.photo.server.starsnap.adapter_infrastructure.star.StarMapper.toStarGroup
import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import com.photo.server.starsnap.domain.star.entity.StarGroup
import com.photo.server.starsnap.domain.star.repository.StarGroupRepository
import org.springframework.data.repository.findByIdOrNull

@Repository
class StarGroupRepositoryImpl(
    private val starGroupCrudRepository: StarGroupCrudRepository
): StarGroupRepository {
    override fun existsByName(name: String): Boolean {
        return starGroupCrudRepository.existsByName(name)
    }

    override fun findSliceBy(pageable: PageRequest): Slice<StarGroup>? {
        return starGroupCrudRepository.findSliceBy(pageable.toSpringPageable())?.toCommonSlice()?.map { it.toStarGroup() }
    }

    override fun findByIdOrNull(id: String): StarGroup? {
        val starGroupEntity = starGroupCrudRepository.findByIdOrNull(id)
        return starGroupEntity?.toStarGroup()
    }

    override fun save(starGroup: StarGroup): StarGroup {
        val starGroupEntity = StarGroupEntity.fromDomain(starGroup)
        return starGroupCrudRepository.save(starGroupEntity).toStarGroup()
    }
}