package com.photo.server.starsnap.adapter_infrastructure.snap.repository

import com.photo.server.starsnap.adapter_infrastructure.snap.SnapMapper.toCommentLike
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.CommentEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.CommentLikeEntity
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.springdata.CommentLikeCrudRepository
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.snap.entity.Comment
import com.photo.server.starsnap.domain.snap.entity.CommentLike
import com.photo.server.starsnap.domain.snap.repository.CommentLikeRepository
import com.photo.server.starsnap.domain.user.entity.User

@Repository
class CommentLikeRepositoryImpl(
    private val commentLikeCrudRepository: CommentLikeCrudRepository
) : CommentLikeRepository {
    override fun save(commentLike: CommentLike): CommentLike {
        val commentLikeEntity = CommentLikeEntity.fromDomain(commentLike)
        return commentLikeCrudRepository.save(commentLikeEntity).toCommentLike()
    }

    override fun delete(commentLike: CommentLike) {
        commentLikeCrudRepository.delete(CommentLikeEntity.fromDomain(commentLike))
    }

    override fun findByUserAndComment(user: User, comment: Comment): CommentLike? {
        val userEntity = UserEntity.fromDomain(user)
        val commentEntity = CommentEntity.fromDomain(comment)
        return commentLikeCrudRepository.findByUserAndComment(userEntity, commentEntity)?.toCommentLike()
    }
}