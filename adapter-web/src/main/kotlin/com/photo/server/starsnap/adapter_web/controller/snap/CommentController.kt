package com.photo.server.starsnap.adapter_web.controller.snap

import com.photo.server.starsnap.adapter_usecase.snap.usecase.CommentUseCaseImpl
import com.photo.server.starsnap.adapter_usecase.snap.usecase.LikeUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.snap.dto.CommentDto
import com.photo.server.starsnap.usecase.snap.dto.CreateCommentRequestDto
import com.photo.server.starsnap.usecase.snap.dto.UpdateCommentRequestDto
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/snap/comment")
class CommentController(
    private val commentUseCaseImpl: CommentUseCaseImpl,
    private val likeUseCaseImpl: LikeUseCaseImpl
) {
    @PostMapping("/create")
    fun createComment(
        @Valid @RequestBody commentDto: CreateCommentRequestDto,
        @AuthenticationPrincipalUserData user: User
    ): CommentDto {
        return commentUseCaseImpl.createComment(commentDto, user)
    }

    @PatchMapping("/update")
    fun updateComment(
        @Valid @RequestBody commentDto: UpdateCommentRequestDto,
        @AuthenticationPrincipalUserData user: User
    ): CommentDto {
        return commentUseCaseImpl.updateComment(commentDto, user)
    }

    @GetMapping("/get")
    fun getComments(
        @RequestParam size: Int,
        @RequestParam page: Int,
        @RequestParam("snap-id") snapId: String
    ): Slice<CommentDto> {
        return commentUseCaseImpl.getComments(size, page, snapId)
    }

    @PatchMapping("/delete")
    fun deleteComments(
        @RequestParam("comment-id") commentId: String,
        @AuthenticationPrincipalUserData user: User
    ): StatusDto {
        return commentUseCaseImpl.deleteComment(commentId, user)
    }

    @PostMapping("/like")
    fun likeComment(
        @AuthenticationPrincipalUserData user: User,
        @RequestParam("comment-id") commentId: String
    ): StatusDto {
        return likeUseCaseImpl.commentLikeSnap(user, commentId)
    }

    @DeleteMapping("/unlike")
    fun unlikeComment(
        @AuthenticationPrincipalUserData user: User,
        @RequestParam("comment-id") commentId: String
    ): StatusDto {
        return likeUseCaseImpl.commentUnlikeSnap(user, commentId)
    }
}