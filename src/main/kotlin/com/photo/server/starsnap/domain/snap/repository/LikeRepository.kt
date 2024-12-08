package com.photo.server.starsnap.domain.snap.repository

import com.photo.server.starsnap.domain.snap.entity.LikeEntity
import com.photo.server.starsnap.domain.snap.entity.SnapEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.repository.CrudRepository
import org.springframework.data.jpa.repository.Query

interface LikeRepository: CrudRepository<LikeEntity, String> {
    @Modifying
    @Query("DELETE FROM LikeEntity like WHERE like.snap = :snap AND like.user = :user")
    fun deleteBySnapAndUser(snap: SnapEntity, user: UserEntity)
}