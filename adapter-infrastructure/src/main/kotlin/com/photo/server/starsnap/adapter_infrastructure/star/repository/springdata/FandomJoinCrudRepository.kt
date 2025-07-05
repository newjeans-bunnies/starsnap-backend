package com.photo.server.starsnap.adapter_infrastructure.star.repository.springdata

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.star.entity.FandomJoinEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.adapter_infrastructure.star.entity.FandomEntity
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

@Repository
interface FandomJoinCrudRepository : CrudRepository<FandomJoinEntity, String> {
    @Query("DELETE FROM FandomJoinEntity f WHERE f.fandom = :fandom AND f.user = :user")
    @Modifying
    fun deleteByFandomIdAndUserId(fandom: FandomEntity, user: UserEntity)

    @Query("SELECT f FROM FandomJoinEntity f WHERE f.fandom = :fandom AND f.user = :user")
    fun findByFandomAndUser(fandom: FandomEntity, user: UserEntity): FandomJoinEntity?

    fun save(fandomJoin: FandomJoinEntity): FandomJoinEntity

}