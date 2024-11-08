package com.photo.server.starsnap.domain.snap.entity

import com.photo.server.starsnap.domain.snap.entity.base.SnapBaseEntity
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
    size: Long,
    type: TYPE,
    source: String,
    imageKey: String,
    dateTaken: LocalDateTime,
    imageWidth: Int,
    imageHeight: Int,
    user: UserEntity,
    @ManyToMany(fetch = FetchType.LAZY) @JoinColumn(
        name = "tag_id",
        nullable = false,
        unique = true
    ) val tags: List<TagEntity>
) : SnapBaseEntity() {
    @Column(name = "title", nullable = false)
    var title: String = title // 글

    @Column(name = "size", nullable = false)
    var size: Long = size
        protected set

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    var type: TYPE = type // 사진 확장자
        protected set

    @Column(name = "source", nullable = false)
    var source: String = source


    @Column(name = "image_key", nullable = false)
    var imageKey: String = imageKey
        protected set

    @Column(name = "date_taken", nullable = false, updatable = false, columnDefinition = "DATE")
    var dateTaken: LocalDateTime = dateTaken

    @Column(name = "image_width", nullable = false)
    var imageWidth: Int = imageWidth
        protected set


    @Column(name = "image_height", nullable = false)
    var imageHeight: Int = imageHeight
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    var userId: UserEntity = user // 만든 사람
        protected set

}