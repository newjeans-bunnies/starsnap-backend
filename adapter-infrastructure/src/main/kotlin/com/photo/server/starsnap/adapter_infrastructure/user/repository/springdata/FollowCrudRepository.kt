package com.photo.server.starsnap.adapter_infrastructure.user.repository.springdata

import jakarta.persistence.LockModeType
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.user.entity.FollowEntity
import com.photo.server.starsnap.domain.user.entity.User

@Repository
interface FollowCrudRepository: CrudRepository<FollowEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT follow FROM FollowEntity follow WHERE follow.followerUser = :user")
    fun getFollowing(pageable: Pageable, user: User): Slice<FollowEntity>?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT follow FROM FollowEntity follow WHERE follow.followingUser = :user")
    fun getFollowers(pageable: Pageable, user: User): Slice<FollowEntity>?

    @Query("SELECT follow FROM FollowEntity follow WHERE follow.followerUser = :followUser AND follow.followingUser.id = :followerUserId")
    fun findByFollowUserIdAndFollowerUserId(followUser: User, followerUserId: String): FollowEntity?

}