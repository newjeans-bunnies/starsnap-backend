package com.photo.server.starsnap.domain.star.repository

import com.photo.server.starsnap.domain.star.entity.FandomEntity
import com.photo.server.starsnap.domain.star.entity.FandomJoinEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import org.springframework.data.repository.CrudRepository

interface FandomJoinRepository : CrudRepository<FandomJoinEntity, String> {
    fun deleteByFandomIdAndUserId(fandom: FandomEntity, user: UserEntity)
}