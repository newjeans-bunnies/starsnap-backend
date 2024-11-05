package com.photo.server.starsnap.domain.user.repository

import com.photo.server.starsnap.domain.user.entity.Oauth2Entity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository: JpaRepository<UserEntity, String> {
    fun findByUsername(username: String): UserEntity?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean

    @Query("SELECT u FROM UserEntity u WHERE :oauth2 IS NULL OR :oauth2 MEMBER OF u.oauth2")
    fun findByOauth2(oauth2: Oauth2Entity?): UserEntity?
}