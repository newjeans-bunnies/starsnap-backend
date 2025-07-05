package com.photo.server.starsnap.adapter_usecase.snap.usecase

import com.photo.server.starsnap.adapter_infrastructure.snap.repository.CommentLikeRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.CommentRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.SnapLikeRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.SnapRepositoryImpl
import com.photo.server.starsnap.domain.snap.entity.CommentLike
import com.photo.server.starsnap.domain.snap.entity.SnapLike
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.exception.snap.error.exception.NotFoundCommentIdException
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.snap.usecase.LikeUseCase
import org.springframework.stereotype.Service
import io.viascom.nanoid.NanoId
import java.time.LocalDateTime

@Service
class LikeUseCaseImpl(
    private val snapLikeRepositoryImpl: SnapLikeRepositoryImpl,
    private val commentLikeRepositoryImpl: CommentLikeRepositoryImpl,
    private val snapRepositoryImpl: SnapRepositoryImpl,
    private val commentRepositoryImpl: CommentRepositoryImpl,
) : LikeUseCase {
    // 스냅 좋아요
    override fun snapLikeSnap(
        user: User,
        snapId: String
    ): StatusDto {
        val snap = snapRepositoryImpl.findByIdOrNull(snapId) ?: throw NotFoundCommentIdException
        val snapLike = SnapLike(user, snap, id = NanoId.generate(16), createdAt = LocalDateTime.now())
        snapLikeRepositoryImpl.save(snapLike)

        return StatusDto("OK", 200)
    }

    // 스냅 좋아요 취소
    override fun snapUnlikeSnap(
        user: User,
        snapId: String
    ): StatusDto {
        val snap = snapRepositoryImpl.findByIdOrNull(snapId) ?: throw NotFoundCommentIdException
        val snapLike = snapLikeRepositoryImpl.findByUserAndSnap(user, snap)
            ?: throw NotFoundCommentIdException

        snapLikeRepositoryImpl.delete(snapLike)

        return StatusDto("OK", 201)
    }

    // 댓글 좋아요
    override fun commentLikeSnap(
        user: User,
        commentId: String
    ): StatusDto {
        val comment = commentRepositoryImpl.findByIdOrNull(commentId) ?: throw NotFoundCommentIdException
        val commentLike = CommentLike(user, comment, LocalDateTime.now(), NanoId.generate(16))

        commentLikeRepositoryImpl.save(commentLike)

        return StatusDto("OK", 200)
    }

    // 댓글 좋아요 취소
    override fun commentUnlikeSnap(
        user: User,
        commentId: String
    ): StatusDto {
        val comment = commentRepositoryImpl.findByIdOrNull(commentId) ?: throw NotFoundCommentIdException
        val commentLike = commentLikeRepositoryImpl.findByUserAndComment(user, comment)
            ?: throw NotFoundCommentIdException

        commentLikeRepositoryImpl.delete(commentLike)

        return StatusDto("OK", 201)
    }
}