package com.photo.server.starsnap.domain.snap.entity

import com.photo.server.starsnap.domain.report.entity.CommentReport
import com.photo.server.starsnap.domain.snap.entity.base.BaseComment
import com.photo.server.starsnap.domain.user.entity.User
import java.time.LocalDateTime

data class Comment(
    val user: User,
    var content: String,
    val snap: Snap,
    var state: Boolean,
    val likeCount: Int,
    override val id: String,
    override val createdAt: LocalDateTime?,
    override var modifiedAt: LocalDateTime?,
) : BaseComment(id, createdAt, modifiedAt){
    val commentReports: List<CommentReport> = mutableListOf()
}