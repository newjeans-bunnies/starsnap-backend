package com.photo.server.starsnap.adapter_infrastructure.snap.entity

import com.photo.server.starsnap.adapter_infrastructure.file.entity.PhotoEntity
import com.photo.server.starsnap.adapter_infrastructure.report.entity.SnapReportEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.base.BaseSnapEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import com.photo.server.starsnap.adapter_infrastructure.star.entity.StarEntity
import com.photo.server.starsnap.adapter_infrastructure.star.entity.StarGroupEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.snap.entity.Snap

@Table(name = "snap")
@EntityListeners(AuditingEntityListener::class)
@Entity
class SnapEntity(
    @Column(name = "title", nullable = false)
    val title: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    val user: UserEntity,
    @Column(name = "state", nullable = false, columnDefinition = "BOOL")
    val state: Boolean,
    @Column(name = "like_count", nullable = false, columnDefinition = "INT UNSIGNED")
    var likeCount: Int,
    @Column(name = "description", nullable = false, columnDefinition = "VARCHAR(255)")
    val description: String = "",

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false, unique = true)
    val tags: List<TagEntity>,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "star_id", nullable = true, columnDefinition = "CHAR(16)")
    var stars: List<StarEntity>,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "star_group_id", nullable = true, columnDefinition = "CHAR(16)")
    var starGroups: List<StarGroupEntity>,

    @OneToMany(mappedBy = "snap", fetch = FetchType.LAZY)
    val likes: List<SnapLikeEntity> = mutableListOf(),

    @OneToMany(mappedBy = "snap", fetch = FetchType.LAZY)
    val saves: List<SaveEntity> = mutableListOf(),

    @OneToMany(mappedBy = "snap", fetch = FetchType.LAZY)
    val snapReports: List<SnapReportEntity> = mutableListOf(),

    @OneToMany(mappedBy = "snap", fetch = FetchType.LAZY)
    val comments: List<CommentEntity> = mutableListOf(),

    @OneToMany(mappedBy = "snap", fetch = FetchType.LAZY, orphanRemoval = false)
    val photos: List<PhotoEntity> = mutableListOf()

) : BaseSnapEntity() {
    fun toDomain(): Snap = Snap(
        title = this.title,
        user = this.user.toDomain(),
        state = this.state,
        likeCount = this.likeCount,
        createdAt = this.createdAt,
        id = this.id,
        modifiedAt = this.modifiedAt,
        tags = this.tags.map { it.toDomain() },
        stars = this.stars.map { it.toDomain() },
        starGroups = this.starGroups.map { it.toDomain() },
        photos = this.photos.map { it.toDomain() },
        comments = this.comments.map { it.toDomain() },
        description = this.description,
    )

    companion object {
        fun fromDomain(snap: Snap) = SnapEntity(
            title = snap.title,
            user = UserEntity.fromDomain(snap.user),
            state = snap.state,
            likeCount = snap.likeCount,
            tags = snap.tags.map { TagEntity.fromDomain(it) },
            stars = snap.stars.map { StarEntity.fromDomain(it) },
            starGroups = snap.starGroups.map { StarGroupEntity.fromDomain(it) },
        )
    }

}