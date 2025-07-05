package com.photo.server.starsnap.domain.user.repository

import com.photo.server.starsnap.domain.user.entity.Oauth2
import com.photo.server.starsnap.domain.user.entity.User
import java.time.LocalDateTime

interface UserRepository {
    fun save(user: User): User
    fun findByUsername(username: String): User?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByOauth2(oauth2:Oauth2): User?
    fun deleteOldUser(sevenDaysAgo: LocalDateTime)
    fun findByIdOrNull(id: String): User?
    fun existsById(id: String): Boolean
}