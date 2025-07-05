package com.photo.server.starsnap.domain.snap.repository

import com.photo.server.starsnap.domain.snap.entity.Snap
import com.photo.server.starsnap.domain.snap.entity.SnapLike
import com.photo.server.starsnap.domain.user.entity.User

interface SnapLikeRepository {
    fun findByUserAndSnap(user: User, snap: Snap): SnapLike?
    fun save(snapLike: SnapLike): SnapLike
    fun delete(snapLike: SnapLike)
}