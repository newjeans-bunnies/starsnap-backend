package com.photo.server.starsnap.domain.report.entity

import com.photo.server.starsnap.domain.report.entity.base.CommentReportBaseEntity
import com.photo.server.starsnap.domain.snap.entity.CommentEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "comment_report")
class CommentReportEntity(
    explanation: String,
    comment: CommentEntity,
    reporter: UserEntity
): CommentReportBaseEntity() {
    @Column(name = "explanation", nullable = false, updatable = false)
    var explanation: String = explanation // 설명
        protected set

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_id", nullable = false, columnDefinition = "CHAR(16)")
    val comment: CommentEntity = comment // snap

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reporter_id", nullable = false, columnDefinition = "CHAR(16)")
    val reporter: UserEntity = reporter // 신고자
}