package com.photo.server.starsnap.domain.snap.entity

import com.photo.server.starsnap.domain.snap.entity.base.BaseLikeEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Table(name = "`like`")
@EntityListeners(AuditingEntityListener::class)
@Entity
class LikeEntity(
    user: UserEntity,
    snap: SnapEntity,
) : BaseLikeEntity() {
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "snap_id", nullable = false, updatable = false, columnDefinition = "CHAR(16)")
    val snap: SnapEntity = snap

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id", nullable = false, updatable = false, columnDefinition = "CHAR(16)")
    val user: UserEntity = user
}