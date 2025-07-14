package com.photo.server.starsnap.adapter_infrastructure.snap.entity

import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import com.photo.server.starsnap.adapter_infrastructure.report.entity.CommentReportEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.base.BaseCommentEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.snap.entity.Comment

@Table(name = "comment")
@EntityListeners(AuditingEntityListener::class)
@Entity
class CommentEntity(
    @OneToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "CHAR(16)")
    val user: UserEntity,
    @Column(name = "content", nullable = false, columnDefinition = "VARCHAR(500)")
    val content: String,
    @Column(name = "state", nullable = false, columnDefinition = "BOOL")
    val state: Boolean,
    @Column(name = "like_count", nullable = false, columnDefinition = "INT UNSIGNED")
    var likeCount: Int = 0,
    @ManyToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER)
    @JoinColumn(name = "snap_id", nullable = false, columnDefinition = "CHAR(16)")
    val snap: SnapEntity
) : BaseCommentEntity() {

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    val commentReports: List<CommentReportEntity> = mutableListOf()

    companion object {
        fun fromDomain(comment: Comment) = CommentEntity(
            user = UserEntity.fromDomain(comment.user),
            content = comment.content,
            state = comment.state,
            snap = SnapEntity.fromDomain(comment.snap)
        )
    }
}