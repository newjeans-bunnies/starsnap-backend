package com.photo.server.starsnap.adapter_usecase.snap.usecase

import com.photo.server.starsnap.adapter_infrastructure.extension.toDomainPageRequest
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.CommentRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.SnapRepositoryImpl
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import com.photo.server.starsnap.domain.snap.entity.Comment
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.domain.user.type.Authority
import com.photo.server.starsnap.exception.global.error.exception.InvalidRoleException
import com.photo.server.starsnap.exception.snap.error.exception.NotFoundCommentIdException
import com.photo.server.starsnap.exception.snap.error.exception.NotFoundSnapIdException
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.snap.dto.CommentDto
import com.photo.server.starsnap.usecase.snap.dto.CreateCommentRequestDto
import com.photo.server.starsnap.usecase.snap.dto.UpdateCommentRequestDto
import com.photo.server.starsnap.usecase.snap.dto.toCommentDto
import com.photo.server.starsnap.usecase.snap.usecase.CommentUseCase
import org.springframework.stereotype.Service
import io.viascom.nanoid.NanoId
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.LocalDateTime

@Service
class CommentUseCaseImpl(
    private val commentRepositoryImpl: CommentRepositoryImpl,
    private val snapRepositoryImpl: SnapRepositoryImpl
): CommentUseCase {
    // 댓글 생성
    override fun createComment(
        commentDto: CreateCommentRequestDto,
        user: User
    ): CommentDto {
        val snap = snapRepositoryImpl.findByIdOrNull(commentDto.snapId) ?: throw NotFoundSnapIdException
        val comment = Comment(
            id = NanoId.generate(16),
            user = user,
            snap = snap,
            content = commentDto.content,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now(),
            state = true,
            likeCount = 0
        )
        commentRepositoryImpl.save(comment)

        return comment.toCommentDto()
    }

    // 댓글 수정
    override fun updateComment(
        commentDto: UpdateCommentRequestDto,
        user: User
    ): CommentDto {
        val comment = commentRepositoryImpl.findByIdOrNull(commentDto.commentId) ?: throw NotFoundCommentIdException

        comment.content = commentDto.content // 메시지 내용 변경
        comment.modifiedAt = LocalDateTime.now() // 메시지 수정 날짜 변경

        commentRepositoryImpl.save(comment)

        return comment.toCommentDto()
    }

    // 댓글 삭제
    override fun deleteComment(
        commentId: String,
        user: User
    ): StatusDto {
        val comment = commentRepositoryImpl.findByIdOrNull(commentId) ?: throw NotFoundCommentIdException

        if(comment.user.id != user.id && user.authority != Authority.ADMIN) {
            throw InvalidRoleException
        }

        comment.state = false
        commentRepositoryImpl.save(comment)

        return StatusDto("successfully", 201)
    }

    // 댓글 조회
    override fun getComments(
        size: Int,
        page: Int,
        snapId: String
    ): Slice<CommentDto> {
        val snap = snapRepositoryImpl.findByIdOrNull(snapId) ?: throw NotFoundSnapIdException

        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )

        val comments = commentRepositoryImpl.findBySnapId(snap, pageRequest.toDomainPageRequest()) ?: throw NotFoundCommentIdException

        return comments.map {
            it.toCommentDto()
        }
    }

}