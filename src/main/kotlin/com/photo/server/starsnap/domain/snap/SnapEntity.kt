package com.photo.server.starsnap.domain.snap

import com.photo.server.starsnap.domain.user.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "snap")
data class SnapEntity(
    @Id
    val id: String, // 아이디
    @Column(name = "title", nullable = false)
    var title: String, // 글
    @OneToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    val createdBy: UserEntity, // 만든 사람
    @Column(name = "create_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    var createdAt: String, // 만든 시간
    @OneToOne
    @JoinColumn(name = "image_id")
    val image: ImageEntity // 게시글
)