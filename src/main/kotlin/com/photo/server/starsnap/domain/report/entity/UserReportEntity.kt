package com.photo.server.starsnap.domain.report.entity

import com.photo.server.starsnap.domain.report.entity.base.UserReportBaseEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_report")
class UserReportEntity(
    explanation: String,
    reporter: UserEntity,
    defendant: UserEntity
) : UserReportBaseEntity() {
    @Column(name = "explanation", nullable = false, updatable = false, columnDefinition = "VARCHAR(500)")
    var explanation: String = explanation // 설명
        protected set

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reporter_id", nullable = false, columnDefinition = "CHAR(16)")
    val reporter: UserEntity = reporter // 신고자

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "defendant_id", nullable = false, columnDefinition = "CHAR(16)")
    val defendant: UserEntity = defendant // 피고인
}