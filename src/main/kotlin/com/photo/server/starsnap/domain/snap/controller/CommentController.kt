package com.photo.server.starsnap.domain.snap.controller

import com.photo.server.starsnap.domain.snap.dto.CommentDto
import com.photo.server.starsnap.domain.snap.dto.CreateCommentRequestDto
import com.photo.server.starsnap.domain.snap.dto.UpdateCommentRequestDto
import com.photo.server.starsnap.domain.snap.service.CommentService
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.global.dto.StatusDto
import jakarta.validation.Valid
import org.springframework.data.domain.Slice
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
    private val commentService: CommentService
) {
    @PostMapping("/create")
    fun createComment(
        @Valid @RequestBody commentDto: CreateCommentRequestDto,
        @AuthenticationPrincipalUserData user: UserEntity
    ): CommentDto {
        return commentService.createComment(commentDto, user)
    }

    @PatchMapping("/update")
    fun updateComment(
        @Valid @RequestBody commentDto: UpdateCommentRequestDto,
        @AuthenticationPrincipalUserData user: UserEntity
    ): CommentDto {
        return commentService.updateComment(commentDto, user.id)
    }

    @GetMapping("/get")
    fun getComments(
        @RequestParam size: Int,
        @RequestParam page: Int,
        @RequestParam("snap-id") snapId: String
    ): Slice<CommentDto> {
        return commentService.getComments(size, page, snapId)
    }

    @PatchMapping("/delete")
    fun deleteComments(
        @RequestParam("comment-id") commentId: String,
        @AuthenticationPrincipalUserData user: UserEntity
    ): StatusDto {
        return commentService.deleteComment(commentId, user.id)
    }
}