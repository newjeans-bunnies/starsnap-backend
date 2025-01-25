package com.photo.server.starsnap.domain.snap.entity

import com.photo.server.starsnap.domain.report.entity.CommentReportEntity
import com.photo.server.starsnap.domain.snap.entity.base.BaseCommentEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Table(name = "comment")
@EntityListeners(AuditingEntityListener::class)
@Entity
class CommentEntity(
    user: UserEntity,
    content: String
) : BaseCommentEntity() {
    @Column(name = "content", nullable = false, columnDefinition = "VARCHAR(500)")
    var content: String = content

    @Column(name = "state", nullable = false, columnDefinition = "BOOL")
    var state: Boolean = true

    @OneToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "CHAR(16)")
    val user: UserEntity = user

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    val comments: List<CommentReportEntity> = mutableListOf()
}