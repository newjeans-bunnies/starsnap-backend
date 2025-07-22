package com.photo.server.starsnap.adapter_infrastructure.user.entity

import com.photo.server.starsnap.adapter_infrastructure.file.entity.PhotoEntity
import com.photo.server.starsnap.adapter_infrastructure.file.entity.VideoEntity
import com.photo.server.starsnap.adapter_infrastructure.report.entity.SnapReportEntity
import com.photo.server.starsnap.adapter_infrastructure.report.entity.UserReportEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SnapLikeEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SnapEntity
import com.photo.server.starsnap.adapter_infrastructure.star.entity.FanEntity
import com.photo.server.starsnap.adapter_infrastructure.star.entity.FandomJoinEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.base.UserBaseEntity
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.domain.user.type.Authority

@Table(name = "user")
@EntityListeners(AuditingEntityListener::class)
@Entity
class UserEntity(
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(5)")
    var authority: Authority,
    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(12)")
    var username: String,
    @Column(columnDefinition = "VARCHAR(60)")
    var password: String?,
    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(320)")
    var email: String,
    @Column(name = "profile_image_url", unique = true, nullable = true)
    var profileImageUrl: String? = null,
    @Column(name = "follower_count", nullable = false, columnDefinition = "INT UNSIGNED")
    var followerCount: Int = 0,
    @Column(name = "follow_count", nullable = false, columnDefinition = "INT UNSIGNED")
    var followingCount: Int = 0,
    @Column(name = "save_count", nullable = false, columnDefinition = "INT UNSIGNED")
    var saveCount: Int = 0,
    @Column(name = "state", nullable = false, columnDefinition = "BOOL")
    var state: Boolean
) : UserBaseEntity() {

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val snaps: List<SnapEntity> = mutableListOf()

    @OneToMany(mappedBy = "followingUser", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val following: List<FollowEntity> = mutableListOf()

    @OneToMany(mappedBy = "followerUser", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val followers: List<FollowEntity> = mutableListOf()

    @OneToMany(mappedBy = "reporter", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val snapReport: List<SnapReportEntity> = mutableListOf()

    @OneToMany(mappedBy = "defendant", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val userReport: List<UserReportEntity> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val oauth2: List<Oauth2Entity> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val fans: List<FanEntity> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val fandomJoins: List<FandomJoinEntity> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val likes: List<SnapLikeEntity> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val photos: List<PhotoEntity> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val videos: List<VideoEntity> = mutableListOf()

    companion object {
        fun fromDomain(user: User) = UserEntity(
            authority = user.authority,
            username = user.username,
            password = user.password,
            email = user.email,
            profileImageUrl = user.profileImageUrl,
            followerCount = user.followerCount,
            followingCount = user.followingCount,
            saveCount = user.saveCount,
            state = user.state
        )
    }

}
