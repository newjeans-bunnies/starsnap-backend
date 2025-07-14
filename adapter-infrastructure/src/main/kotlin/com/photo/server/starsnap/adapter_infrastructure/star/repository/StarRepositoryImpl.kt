package com.photo.server.starsnap.adapter_infrastructure.star.repository

import com.photo.server.starsnap.adapter_infrastructure.star.StarMapper.toStar
import com.photo.server.starsnap.adapter_infrastructure.star.entity.StarEntity
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.star.repository.springdata.StarCrudRepository
import com.photo.server.starsnap.domain.star.entity.Star
import com.photo.server.starsnap.domain.star.repository.StarRepository
import org.springframework.data.repository.findByIdOrNull

@Repository
class StarRepositoryImpl(
    private val starCrudRepository: StarCrudRepository
): StarRepository {
    override fun existsByName(name: String): Boolean {
        return starCrudRepository.existsByName(name)
    }

    override fun existsByNickname(nickname: String): Boolean {
        return starCrudRepository.existsByNickname(nickname)
    }

    override fun findByIdOrNull(starId: String): Star? {
        val starEntity = starCrudRepository.findByIdOrNull(starId)
        return starEntity?.toStar()
    }

    override fun save(star: Star): Star {
        return starCrudRepository.save(StarEntity.fromDomain(star)).toStar()
    }

    override fun existsById(id: String): Boolean {
        return starCrudRepository.existsById(id)
    }
}