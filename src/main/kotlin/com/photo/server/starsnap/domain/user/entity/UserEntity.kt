package com.photo.server.starsnap.domain.user.entity

import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.report.entity.SnapReportEntity
import com.photo.server.starsnap.domain.report.entity.UserReportEntity
import com.photo.server.starsnap.domain.snap.SnapEntity
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder

@Table(name = "user")
@Entity
data class UserEntity(
    @Id
    @Column(name = "id", columnDefinition = "CHAR(16)", updatable = false)
    val id: String,
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(5)")
    val authority: Authority,
    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(12)")
    var username: String,
    @Column(nullable = false, columnDefinition = "VARCHAR(60)")
    var password: String,
    @Column(nullable = false, columnDefinition = "VARCHAR(320)")
    val email: String,
    @Column(name = "profile_image_url", unique = true, nullable = true)
    var profileImageUrl: String? = null,
    @Column(name = "follow_count", nullable = false, columnDefinition = "INT UNSIGNED")
    var followingCount: Int,
    @Column(name = "follower_count", nullable = false, columnDefinition = "INT UNSIGNED")
    var followerCount: Int,

    @OneToMany(mappedBy = "userId", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val snap: List<SnapEntity> = mutableListOf(),

    @OneToMany(mappedBy = "followingUser", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val following: List<FollowEntity> = mutableListOf(),
    @OneToMany(mappedBy = "followerUser", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val followers: List<FollowEntity> = mutableListOf(),

    @OneToMany(mappedBy = "reporter", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val snapReport: List<SnapReportEntity> = mutableListOf(),
    @OneToMany(mappedBy = "defendant", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val userReport: List<UserReportEntity> = mutableListOf(),
) {
    fun hashPassword(passwordEncoder: PasswordEncoder) {
        this.password = passwordEncoder.encode(this.password)
    }

    @PreRemove
    fun removeUserData() {
        this.snap.map {
            println("사진: " + it.imageKey)
        }
        this.following.map {
            println("팔로우: " + it.followingUser.username)
        }
        this.followers.map {
            it.followingUser.followerCount -= 1
            println("팔로워: " + it.followingUser.username)
        }
    }

}
