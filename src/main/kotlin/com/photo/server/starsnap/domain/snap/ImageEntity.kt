package com.photo.server.starsnap.domain.snap

import com.photo.server.starsnap.domain.snap.type.TYPE
import com.photo.server.starsnap.domain.user.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "image")
data class ImageEntity(
    @Id
    val id: String, // 사진 아이디
    @Column(name = "size", nullable = false)
    var size: Long, // 사진 크기
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    var type: TYPE, // 사진 확장자
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    var createdAt: String, // 생성 날짜
    @Column(name ="source", nullable = false)
    var source: String, // 출처
    @Column(name = "date_taken", nullable = false, updatable = false, columnDefinition = "DATE")
    var dateTaken: String,
    @OneToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    val createdBy: UserEntity // 만든 사람
)
