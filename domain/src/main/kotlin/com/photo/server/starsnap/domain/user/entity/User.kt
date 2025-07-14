package com.photo.server.starsnap.domain.user.entity

import com.photo.server.starsnap.domain.report.entity.SnapReport
import com.photo.server.starsnap.domain.report.entity.UserReport
import com.photo.server.starsnap.domain.snap.entity.Snap
import com.photo.server.starsnap.domain.snap.entity.SnapLike
import com.photo.server.starsnap.domain.star.entity.Fan
import com.photo.server.starsnap.domain.star.entity.FandomJoin
import com.photo.server.starsnap.domain.user.entity.base.UserBase
import com.photo.server.starsnap.domain.user.type.Authority
import java.time.LocalDateTime

data class User(
    val authority: Authority,
    var username: String,
    var password: String? = null,
    val email: String,
    val profileImageUrl: String? = null,
    val followerCount: Int = 0,
    val followingCount: Int = 0,
    val saveCount: Int = 0,
    var state: Boolean = true,
    override val createdAt: LocalDateTime?,
    override val id: String,
    override var modifiedAt: LocalDateTime?
) : UserBase(id, createdAt, modifiedAt) {
    val snaps: List<Snap> = mutableListOf()
    val followings: List<Follow> = mutableListOf()
    val followers: List<Follow> = mutableListOf()
    val snapReports: List<SnapReport> = mutableListOf()
    val userReports: List<UserReport> = mutableListOf()
    val oauth2: List<Oauth2> = mutableListOf()
    val fans: List<Fan> = mutableListOf()
    val fandomJoins: List<FandomJoin> = mutableListOf()
    val likes: List<SnapLike> = mutableListOf()
}