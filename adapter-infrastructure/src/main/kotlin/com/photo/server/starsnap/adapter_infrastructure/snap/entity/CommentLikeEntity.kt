package com.photo.server.starsnap.adapter_infrastructure.snap.entity

import com.photo.server.starsnap.adapter_infrastructure.snap.entity.base.BaseLikeEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.snap.entity.CommentLike

@Table(name = "comment_like")
@EntityListeners(AuditingEntityListener::class)
@Entity
class CommentLikeEntity(
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id", nullable = false, updatable = false, columnDefinition = "CHAR(16)")
    val user: UserEntity,
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "comment_id", nullable = false, updatable = false, columnDefinition = "CHAR(16)")
    val comment: CommentEntity,
) : BaseLikeEntity() {

    companion object {
        fun fromDomain(commentLike: CommentLike) = CommentLikeEntity(
            user = UserEntity.fromDomain(commentLike.user),
            comment = CommentEntity.fromDomain(commentLike.comment),
        )
    }

    // Follow 엔티티가 삭제 되면 자동으로 팔로워와 팔로우 숫자 -1
    @PostRemove
    fun unlike() {
        comment.likeCount -= 1
    }

    // Follow 엔티티가 생성 되면 자동으로 팔로워와 팔로우 숫자 +1
    @PrePersist
    fun like() {
        comment.likeCount += 1
    }
}