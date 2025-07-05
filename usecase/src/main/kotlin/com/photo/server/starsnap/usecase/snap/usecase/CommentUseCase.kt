package com.photo.server.starsnap.usecase.snap.usecase

import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.snap.dto.CommentDto
import com.photo.server.starsnap.usecase.snap.dto.CreateCommentRequestDto
import com.photo.server.starsnap.usecase.snap.dto.UpdateCommentRequestDto

interface CommentUseCase {
    fun createComment(commentDto: CreateCommentRequestDto, user: User): CommentDto

    fun updateComment(commentDto: UpdateCommentRequestDto, user: User): CommentDto

    fun deleteComment(commentId: String, user: User): StatusDto

    fun getComments(size: Int, page: Int, snapId: String): Slice<CommentDto>
}