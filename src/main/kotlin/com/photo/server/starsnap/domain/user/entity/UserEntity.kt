package com.photo.server.starsnap.domain.user.entity

import com.photo.server.starsnap.domain.auth.entity.PassKeysEntity
import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.report.entity.SnapReportEntity
import com.photo.server.starsnap.domain.report.entity.UserReportEntity
import com.photo.server.starsnap.domain.snap.entity.SnapEntity
import com.photo.server.starsnap.domain.star.entity.FanEntity
import com.photo.server.starsnap.domain.star.entity.FandomJoinEntity
import com.photo.server.starsnap.domain.user.entity.base.UserBaseEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.security.crypto.password.PasswordEncoder

@Table(name = "user")
@EntityListeners(AuditingEntityListener::class)
@Entity
class UserEntity(
    authority: Authority,
    username: String,
    password: String? = null,
    email: String,
    profileImageUrl: String? = null,
    followerCount: Int = 0,
    followingCount: Int = 0,
    state: Boolean
) : UserBaseEntity() {

    @Column(name = "state", nullable = false, columnDefinition = "BOOL")
    var state: Boolean = state

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(5)")
    var authority: Authority = authority
        protected set

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(12)")
    var username: String = username

    @Column(columnDefinition = "VARCHAR(60)")
    var password: String? = password

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(320)")
    var email: String = email
        protected set

    @Column(name = "profile_image_url", unique = true, nullable = true)
    var profileImageUrl: String? = profileImageUrl

    @Column(name = "follow_count", nullable = false, columnDefinition = "INT UNSIGNED")
    var followingCount = followingCount

    @Column(name = "follower_count", nullable = false, columnDefinition = "INT UNSIGNED")
    var followerCount = followerCount

    @OneToMany(mappedBy = "userId", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val snaps: List<SnapEntity> = mutableListOf()

    @OneToMany(mappedBy = "followingUser", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val following: List<FollowEntity> = mutableListOf()

    @OneToMany(mappedBy = "followerUser", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val followers: List<FollowEntity> = mutableListOf()

    @OneToMany(mappedBy = "reporter", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val snapReport: List<SnapReportEntity> = mutableListOf()

    @OneToMany(mappedBy = "defendant", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val userReport: List<UserReportEntity> = mutableListOf()

    @OneToMany(mappedBy = "userId", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val oauth2: List<Oauth2Entity> = mutableListOf()

    @OneToMany(mappedBy = "userId", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val passKeys: List<PassKeysEntity> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val fans: List<FanEntity> = mutableListOf()

    @OneToMany(mappedBy = "userId", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val fandomJoins: List<FandomJoinEntity> = mutableListOf()


    fun hashPassword(passwordEncoder: PasswordEncoder) {
        this.password = passwordEncoder.encode(this.password)
    }

}
