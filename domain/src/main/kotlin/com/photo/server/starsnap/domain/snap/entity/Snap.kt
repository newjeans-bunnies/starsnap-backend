package com.photo.server.starsnap.domain.snap.entity

import com.photo.server.starsnap.domain.file.entity.Photo
import com.photo.server.starsnap.domain.report.entity.SnapReport
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
    val description: String,
    override val createdAt: LocalDateTime?,
    override val id: String,
    override var modifiedAt: LocalDateTime?
) : BaseSnap(id, createdAt, modifiedAt){
    var photos: List<Photo> = mutableListOf()
    val likes: List<SnapLike> = mutableListOf()
    val saves: List<Save> = mutableListOf()
    val snapReports: List<SnapReport> = mutableListOf()
    val comments: List<Comment> = mutableListOf()
    var tags: List<Tag> = mutableListOf()
    var stars: List<Star> = mutableListOf()
    var starGroups: List<StarGroup> = mutableListOf()
}