package com.photo.server.starsnap.adapter_infrastructure.star.repository

import com.photo.server.starsnap.adapter_infrastructure.star.StarMapper.toFandomJoin
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.star.entity.FandomEntity
import com.photo.server.starsnap.adapter_infrastructure.star.entity.FandomJoinEntity
import com.photo.server.starsnap.adapter_infrastructure.star.repository.springdata.FandomJoinCrudRepository
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.star.entity.Fandom
import com.photo.server.starsnap.domain.star.entity.FandomJoin
import com.photo.server.starsnap.domain.star.repository.FandomJoinRepository
import com.photo.server.starsnap.domain.user.entity.User

@Repository
class FandomJoinRepositoryImpl(
    private val fandomJoinCrudRepository: FandomJoinCrudRepository
) : FandomJoinRepository {
    override fun deleteByFandomIdAndUserId(fandom: Fandom, user: User) {
        return fandomJoinCrudRepository.deleteByFandomIdAndUserId(
            FandomEntity.fromDomain(fandom),
            UserEntity.fromDomain(user)
        )
    }

    override fun findByFandomAndUser(fandom: Fandom, user: User): FandomJoin? {
        return fandomJoinCrudRepository.findByFandomAndUser(
            FandomEntity.fromDomain(fandom),
            UserEntity.fromDomain(user)
        )?.toFandomJoin()
    }

    override fun save(fandomJoin: FandomJoin): FandomJoin {
        val fandomJoinEntity = FandomJoinEntity.fromDomain(fandomJoin)
        return fandomJoinEntity.toFandomJoin()
    }

    override fun delete(fandomJoin: FandomJoin) {
        fandomJoinCrudRepository.delete(FandomJoinEntity.fromDomain(fandomJoin))
    }
}