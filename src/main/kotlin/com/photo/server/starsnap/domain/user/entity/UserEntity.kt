package com.photo.server.starsnap.domain.user.entity

import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.snap.SnapEntity
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder


@Table(name = "user")
@Entity
data class UserEntity(
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(12)", updatable = false)
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
    val profileImageUrl: String? = null,
    @OneToMany(mappedBy = "userId", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val snap: List<SnapEntity> = mutableListOf()
) {
    fun hashPassword(passwordEncoder: PasswordEncoder) {
        this.password = passwordEncoder.encode(this.password)
    }
}
