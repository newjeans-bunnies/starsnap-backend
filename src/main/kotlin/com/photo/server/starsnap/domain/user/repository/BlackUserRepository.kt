package com.photo.server.starsnap.domain.user.repository

import com.photo.server.starsnap.domain.user.entity.BlackUserEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface BlackUserRepository : CrudRepository<BlackUserEntity, String> {
    @Modifying
    @Query("delete from BlackUserEntity balckuser where balckuser.user = :userId and balckuser.blackUser = :blackUser")
    fun deleteByUserAndBlackUser(user: UserEntity, blackUser: UserEntity)


    @Query("select balckUser from BlackUserEntity balckUser where balckUser.user = :user")
    fun findSliceBy(pageable: Pageable, user: UserEntity): Slice<BlackUserEntity>?

    @Query("select balckUser from BlackUserEntity balckUser where balckUser.user = :user")
    fun findUserBy(user: UserEntity): List<UserEntity>
}