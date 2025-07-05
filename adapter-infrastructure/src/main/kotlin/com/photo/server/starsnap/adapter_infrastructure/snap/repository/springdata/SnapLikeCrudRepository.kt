package com.photo.server.starsnap.adapter_infrastructure.snap.repository.springdata

import org.springframework.data.repository.CrudRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SnapLikeEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SnapEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity

@Repository
interface SnapLikeCrudRepository: CrudRepository<SnapLikeEntity, String> {
    @Query("SELECT s FROM SnapLikeEntity s WHERE s.user = :user AND s.snap = :snap")
    fun findByUserAndSnap(user: UserEntity, snap: SnapEntity): SnapLikeEntity?
}