package com.photo.server.starsnap.domain.snap

import com.photo.server.starsnap.domain.snap.type.TYPE
import com.photo.server.starsnap.domain.user.entity.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "snap")
data class SnapEntity(
    @Id
    val id: String, // 아이디
    @Column(name = "title", nullable = false)
    var title: String, // 글
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    var createdAt: String, // 만든 시간
    @Column(name = "size", nullable = false)
    var size: Long, // 사진 크기
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    var type: TYPE, // 사진 확장자
    @Column(name = "source", nullable = false)
    var source: String, // 출처
    @Column(name = "image_key", nullable = false)
    var imageKey: String, // 이미지 키
    @Column(name = "date_taken", nullable = false, updatable = false, columnDefinition = "DATE")
    var dateTaken: String, // 사진 찍은 날짜
    @OneToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    val createdBy: UserEntity, // 만든 사람
)