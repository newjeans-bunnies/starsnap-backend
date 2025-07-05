package com.photo.server.starsnap.adapter_infrastructure.snap.repository

import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SnapEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SnapLikeEntity
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.springdata.SnapLikeCrudRepository
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.snap.entity.Snap
import com.photo.server.starsnap.domain.snap.entity.SnapLike
import com.photo.server.starsnap.domain.snap.repository.SnapLikeRepository
import com.photo.server.starsnap.domain.user.entity.User

@Repository
class SnapLikeRepositoryImpl(
    private val snapLikeCrudRepository: SnapLikeCrudRepository
) : SnapLikeRepository {

    override fun save(snapLike: SnapLike): SnapLike {
        val snapLikeEntity = SnapLikeEntity.fromDomain(snapLike)
        return snapLikeCrudRepository.save(snapLikeEntity).toDomain()
    }

    override fun findByUserAndSnap(
        user: User, snap: Snap
    ): SnapLike? {
        val userEntity = UserEntity.fromDomain(user)
        val snapEntity = SnapEntity.fromDomain(snap)
        return snapLikeCrudRepository.findByUserAndSnap(userEntity, snapEntity)?.toDomain()
    }

    override fun delete(snapLike: SnapLike) {
        snapLikeCrudRepository.delete(SnapLikeEntity.fromDomain(snapLike))
    }
}