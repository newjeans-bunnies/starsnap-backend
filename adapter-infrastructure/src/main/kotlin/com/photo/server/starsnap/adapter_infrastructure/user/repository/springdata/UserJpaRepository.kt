package com.photo.server.starsnap.adapter_infrastructure.user.repository.springdata

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.user.entity.Oauth2Entity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

@Repository
interface UserJpaRepository: JpaRepository<UserEntity, String> {
    fun findByUsername(username: String): UserEntity?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean

    @Query("SELECT u FROM UserEntity u WHERE :oauth2 IS NULL OR :oauth2 MEMBER OF u.oauth2")
    fun findByOauth2(oauth2: Oauth2Entity): UserEntity?

    @Transactional
    @Modifying
    @Query("DELETE FROM UserEntity u WHERE u.state = false AND u.modifiedAt <= :sevenDaysAgo")
    fun deleteOldUser(sevenDaysAgo: LocalDateTime)
}