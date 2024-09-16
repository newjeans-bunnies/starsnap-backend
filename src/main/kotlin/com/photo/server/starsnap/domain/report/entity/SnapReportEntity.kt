package com.photo.server.starsnap.domain.report.entity

import com.photo.server.starsnap.domain.snap.type.TYPE
import jakarta.persistence.*

@Entity
@Table(name = "snap_report")
data class SnapReportEntity(
    @Id
    val id: String,
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    var createdAt: String, // 신고 시간
    @Column(name = "explanation", nullable = false, updatable = false)
    var explanation: String, // 설명
    @Column(name = "reporter_id", nullable = false, updatable = false)
    val reporterId: String,
    @Column(name = "reporter_username", nullable = false, updatable = false)
    val reporterUsername: String,
    @Column(name = "reporter_email", nullable = false, updatable = false)
    val reporterEmail: String,
    @Column(name = "reporter_authority", nullable = false, updatable = false)
    val reporterAuthority: String,
    @Column(name = "snap_id", nullable = false, updatable = false)
    val snapId: String,
    @Column(name = "snap_title", nullable = false, updatable = false)
    val snapTitle: String,
    @Column(name = "snap_created_at", nullable = false, updatable = false, columnDefinition = "DATE")
    val snapCreatedAt: String,
    @Column(name = "snap_size", nullable = false, updatable = false)
    val snapSize: Long,
    @Enumerated(EnumType.STRING)
    @Column(name = "snap_type", nullable = false, updatable = false)
    val snapType: TYPE,
    @Column(name = "snap_source", nullable = false, updatable = false)
    val snapSource: String,
    @Column(name = "snap_image_key", nullable = false, updatable = false)
    val snapImageKey: String,
    @Column(name = "snap_date_taken", nullable = false, updatable = false)
    val snapDateTaken: String,
    @Column(name = "snap_image_width", nullable = false, updatable = false)
    val snapImageWidth: Int,
    @Column(name = "snap_image_height", nullable = false, updatable = false)
    val snapImageHeight: Int,
)