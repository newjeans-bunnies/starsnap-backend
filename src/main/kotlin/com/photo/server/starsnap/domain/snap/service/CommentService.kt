package com.photo.server.starsnap.domain.snap.service

import com.photo.server.starsnap.domain.snap.dto.CommentDto
import com.photo.server.starsnap.domain.snap.dto.CreateCommentRequestDto
import com.photo.server.starsnap.domain.snap.dto.UpdateCommentRequestDto
import com.photo.server.starsnap.domain.snap.dto.toCommentDto
import com.photo.server.starsnap.domain.snap.entity.CommentEntity
import com.photo.server.starsnap.domain.snap.repository.CommentRepository
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.dto.StatusDto
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository
) {
    fun createComment(commentDto: CreateCommentRequestDto, user: UserEntity): CommentDto {
        val comment = CommentEntity(user, commentDto.content)
        commentRepository.save(comment)
        return comment.toCommentDto()
    }

    fun updateComment(commentDto: UpdateCommentRequestDto, userId: String): CommentDto {
        val comment =
            commentRepository.findByIdOrNull(commentDto.commentId) ?: throw RuntimeException("존재 하지 않는 CommentId")
        if (comment.user.id != userId) throw RuntimeException("권한 없음")

        comment.content = commentDto.content
        commentRepository.save(comment)
        return comment.toCommentDto()
    }

    fun deleteComment(commentId: String, userId: String): StatusDto {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw RuntimeException("존재 하지 않는 CommentId")
        if (comment.user.id != userId) throw RuntimeException("권한 없음")
        comment.state = false
        commentRepository.save(comment)
        return StatusDto("OK", 200)
    }

    fun getComments(size: Int, page: Int, snapId: String): Slice<CommentDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val comments = commentRepository.findSliceBy(pageRequest) ?: throw RuntimeException("존재하지 않는 snapId")
        return comments.map {
            it.toCommentDto()
        }
    }
}