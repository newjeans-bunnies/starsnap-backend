package com.photo.server.starsnap.domain.user.repository

import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.user.entity.BlackUser
import com.photo.server.starsnap.domain.user.entity.User

interface BlackUserRepository {
    fun deleteByUserAndBlackUser(user: User, blackUser: User)
    fun findSliceBy(pageable: PageRequest, user: User): Slice<BlackUser>?
    fun findUserBy(user: User): List<User>
    fun save(blackUser: BlackUser): BlackUser
}