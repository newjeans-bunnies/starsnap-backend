package com.photo.server.starsnap.domain.snap.entity

import com.photo.server.starsnap.domain.file.entity.FileEntity
import com.photo.server.starsnap.domain.report.entity.SnapReportEntity
import com.photo.server.starsnap.domain.snap.entity.base.BaseSnapEntity
 import com.photo.server.starsnap.domain.star.entity.StarEntity
import com.photo.server.starsnap.domain.star.entity.StarGroupEntity
import com.photo.server.starsnap.global.utils.type.TYPE
import com.photo.server.starsnap.domain.user.entity.UserEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Table(name = "snap")
@EntityListeners(AuditingEntityListener::class)
@Entity
class SnapEntity(
    title: String,
    user: UserEntity,
    state: Boolean,
    likeCount: Int,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false, unique = true)
    val tags: List<TagEntity>,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "star_id", nullable = true, columnDefinition = "CHAR(16)")
    var star: List<StarEntity>,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "star_group_id", nullable = true, columnDefinition = "CHAR(16)")
    var starGroup: List<StarGroupEntity>

) : BaseSnapEntity() {
    @Column(name = "state", nullable = false, columnDefinition = "BOOL")
    var state: Boolean = state

    @Column(name = "title", nullable = false)
    var title: String = title // 글

    @Column(name = "like_count", nullable = false, columnDefinition = "INT UNSIGNED")
    var likeCount: Int = likeCount

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    var user: UserEntity = user // 만든 사람
        protected set

    @OneToMany(mappedBy = "snap", fetch = FetchType.LAZY)
    val likes: List<LikeEntity> = mutableListOf()

    @OneToMany(mappedBy = "snap", fetch = FetchType.LAZY)
    val saves: List<SaveEntity> = mutableListOf()

    @OneToMany(mappedBy = "snap", fetch = FetchType.LAZY)
    val snapReports: List<SnapReportEntity> = mutableListOf()

    @OneToMany(mappedBy = "snap", fetch = FetchType.LAZY)
    val comments: List<CommentEntity> = mutableListOf()

    @OneToMany(mappedBy = "snap", fetch = FetchType.LAZY)
    val files: List<FileEntity> = mutableListOf()
}