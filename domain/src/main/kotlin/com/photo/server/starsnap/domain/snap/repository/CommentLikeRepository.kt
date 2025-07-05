package com.photo.server.starsnap.domain.snap.repository

import com.photo.server.starsnap.domain.snap.entity.Comment
import com.photo.server.starsnap.domain.snap.entity.CommentLike
import com.photo.server.starsnap.domain.user.entity.User

interface CommentLikeRepository {
    fun findByUserAndComment(user: User, comment: Comment): CommentLike?
    fun save(commentLike: CommentLike): CommentLike
    fun delete(commentLike: CommentLike)
}