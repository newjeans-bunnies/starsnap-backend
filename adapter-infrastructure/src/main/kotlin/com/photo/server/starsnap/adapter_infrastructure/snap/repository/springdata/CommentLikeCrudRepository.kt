package com.photo.server.starsnap.adapter_infrastructure.snap.repository.springdata

import com.photo.server.starsnap.adapter_infrastructure.snap.entity.CommentEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.CommentLikeEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity

@Repository
interface CommentLikeCrudRepository: CrudRepository<CommentLikeEntity, String> {
    @Query("SELECT cl FROM CommentLikeEntity cl WHERE cl.user = :user AND cl.comment = :comment")
    fun findByUserAndComment(user: UserEntity, comment: CommentEntity): CommentLikeEntity?
}