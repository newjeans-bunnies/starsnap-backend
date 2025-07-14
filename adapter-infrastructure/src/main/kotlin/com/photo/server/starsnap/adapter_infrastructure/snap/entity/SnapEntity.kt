package com.photo.server.starsnap.adapter_infrastructure.snap.entity

import com.photo.server.starsnap.adapter_infrastructure.file.entity.PhotoEntity
import com.photo.server.starsnap.adapter_infrastructure.report.entity.SnapReportEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.base.BaseSnapEntity
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import com.photo.server.starsnap.adapter_infrastructure.star.entity.StarEntity
import com.photo.server.starsnap.adapter_infrastructure.star.entity.StarGroupEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.snap.entity.Snap
import jakarta.persistence.*

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
    val description: String = ""

) : BaseSnapEntity() {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "snap_tag",
        joinColumns = [JoinColumn(name = "snap_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    val tags: List<TagEntity> = mutableListOf()

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "snap_star",
        joinColumns = [JoinColumn(name = "snap_id")],
        inverseJoinColumns = [JoinColumn(name = "star_id")]
    )
    var stars: List<StarEntity> = mutableListOf()

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "snap_stargroup",
        joinColumns = [JoinColumn(name = "snap_id")],
        inverseJoinColumns = [JoinColumn(name = "stargroup_id")]
    )
    var starGroups: List<StarGroupEntity> = mutableListOf()

    @OneToMany(mappedBy = "snap", fetch = FetchType.LAZY)
    val likes: List<SnapLikeEntity> = mutableListOf()

    @OneToMany(mappedBy = "snap", fetch = FetchType.LAZY)
    val saves: List<SaveEntity> = mutableListOf()

    @OneToMany(mappedBy = "snap", fetch = FetchType.LAZY)
    val snapReports: List<SnapReportEntity> = mutableListOf()

    @OneToMany(mappedBy = "snap", fetch = FetchType.LAZY)
    val comments: List<CommentEntity> = mutableListOf()

    @OneToMany(mappedBy = "snap", fetch = FetchType.LAZY, orphanRemoval = false)
    val photos: List<PhotoEntity> = mutableListOf()

    companion object {
        fun fromDomain(snap: Snap) = SnapEntity(
            title = snap.title,
            user = UserEntity.fromDomain(snap.user),
            state = snap.state,
            likeCount = snap.likeCount,
            description = snap.description
        )
    }
}