package com.photo.server.starsnap.domain.report.entity

import com.photo.server.starsnap.domain.report.entity.base.SnapReportBaseEntity
import com.photo.server.starsnap.domain.snap.entity.SnapEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "snap_report")
class SnapReportEntity(
    snap: SnapEntity,
    reporter: UserEntity,
    explanation: String
) : SnapReportBaseEntity() {
    @Column(name = "explanation", nullable = false, updatable = false)
    var explanation: String = explanation // 설명
        protected set

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "snap_id", nullable = false, columnDefinition = "CHAR(16)")
    val snap: SnapEntity = snap // snap

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reporter_id", nullable = false, columnDefinition = "CHAR(16)")
    val reporter: UserEntity = reporter // 신고자
}