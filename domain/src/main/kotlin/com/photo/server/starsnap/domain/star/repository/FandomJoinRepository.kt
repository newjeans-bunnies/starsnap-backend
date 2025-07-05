package com.photo.server.starsnap.domain.star.repository

import com.photo.server.starsnap.domain.star.entity.Fandom
import com.photo.server.starsnap.domain.star.entity.FandomJoin
import com.photo.server.starsnap.domain.user.entity.User

interface FandomJoinRepository {
    fun deleteByFandomIdAndUserId(fandom: Fandom, user: User)
    fun save(fandomJoin: FandomJoin): FandomJoin
    fun findByFandomAndUser(fandom: Fandom, user: User): FandomJoin?
    fun delete(fandomJoin: FandomJoin)
}