package com.photo.server.starsnap.adapter_infrastructure.user.repository.springdata

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.user.entity.BlackUserEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity

@Repository
interface BlackUserCrudRepository : CrudRepository<BlackUserEntity, String> {
    @Modifying
    @Query("delete from BlackUserEntity balckuser where balckuser.userEntity = :userId and balckuser.blackUserEntity = :blackUser")
    fun deleteByUserAndBlackUser(user: UserEntity, blackUser: UserEntity)


    @Query("select balckUser from BlackUserEntity balckUser where balckUser.userEntity = :user")
    fun findSliceBy(pageable: Pageable, user: UserEntity): Slice<BlackUserEntity>?

    @Query("select balckUser from BlackUserEntity balckUser where balckUser.userEntity = :user")
    fun findUserBy(user: UserEntity): List<UserEntity>
}