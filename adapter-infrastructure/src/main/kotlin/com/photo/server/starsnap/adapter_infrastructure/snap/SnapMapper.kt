package com.photo.server.starsnap.adapter_infrastructure.snap

import com.photo.server.starsnap.adapter_infrastructure.snap.entity.CommentEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.CommentLikeEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SaveEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SnapEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SnapLikeEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.TagEntity
import com.photo.server.starsnap.adapter_infrastructure.user.UserMapper.toUser
import com.photo.server.starsnap.domain.snap.entity.Comment
import com.photo.server.starsnap.domain.snap.entity.CommentLike
import com.photo.server.starsnap.domain.snap.entity.Save
import com.photo.server.starsnap.domain.snap.entity.Snap
import com.photo.server.starsnap.domain.snap.entity.SnapLike
import com.photo.server.starsnap.domain.snap.entity.Tag

object SnapMapper {

    fun CommentEntity.toComment() = Comment(
        id = this.id,
        createdAt = this.createdAt,
        modifiedAt = this.modifiedAt,
        content = this.content,
        user = this.user.toUser(),
        snap = this.snap.toSnap(),
        state = this.state,
        likeCount = this.likeCount
    )

    fun CommentLikeEntity.toCommentLike() = CommentLike(
        id = this.id,
        createdAt = this.createdAt,
        user = this.user.toUser(),
        comment = this.comment.toComment()
    )

    fun SaveEntity.toSave(
    ) = Save(
        id = this.id,
        saveTime = this.saveTime,
        snap = this.snap.toSnap(),
        user = this.user.toUser()
    )

    fun SnapEntity.toSnap() = Snap(
        id = this.id,
        createdAt = this.createdAt,
        modifiedAt = this.modifiedAt,
        title = this.title,
        user = this.user.toUser(),
        state = this.state,
        likeCount = this.likeCount,
        description = this.description
    )

    fun SnapLikeEntity.toSnapLike() = SnapLike(
        id = this.id,
        createdAt = this.createdAt,
        user = this.user.toUser(),
        snap = this.snap.toSnap()
    )

    fun TagEntity.toTag() = Tag(
        id = this.id,
        name = this.name,
        count = this.count
    )
}