package com.photo.server.starsnap.domain.user.repository

import com.photo.server.starsnap.domain.user.entity.FollowEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import jakarta.persistence.LockModeType
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface FollowRepository: CrudRepository<FollowEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByFollowUserAndUser(followUser: UserEntity, user: UserEntity): FollowEntity

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT follow FROM FollowEntity follow WHERE follow.user = :userId")
    fun getFollow(pageable: Pageable, userId: String): Slice<FollowEntity>?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT follow FROM FollowEntity follow WHERE follow.followUser.id = :userId")
    fun getFollowers(pageable: Pageable, userId: String): Slice<FollowEntity>?

}