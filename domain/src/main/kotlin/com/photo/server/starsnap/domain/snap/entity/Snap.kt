package com.photo.server.starsnap.domain.snap.entity

import com.photo.server.starsnap.domain.file.entity.Photo
import com.photo.server.starsnap.domain.snap.entity.base.BaseSnap
import com.photo.server.starsnap.domain.star.entity.Star
import com.photo.server.starsnap.domain.star.entity.StarGroup
import com.photo.server.starsnap.domain.user.entity.User
import java.time.LocalDateTime

data class Snap(
    var title: String,
    val user: User,
    var state: Boolean,
    val likeCount: Int,
    val photos: List<Photo>,
    val comments: List<Comment>,
    val description: String,
    val tags: List<Tag>,
    var stars: List<Star>,
    var starGroups: List<StarGroup>,
    override val createdAt: LocalDateTime?,
    override val id: String,
    override var modifiedAt: LocalDateTime?
) : BaseSnap(id, createdAt, modifiedAt)