package com.photo.server.starsnap.adapter_infrastructure.user.repository

import com.photo.server.starsnap.adapter_infrastructure.user.UserMapper.toUser
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.user.entity.Oauth2Entity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.adapter_infrastructure.user.repository.springdata.UserJpaRepository
import com.photo.server.starsnap.domain.user.entity.Oauth2
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
): UserRepository {
    override fun save(user: User): User {
        // 1) 도메인 → JPA 엔티티
        val entity = UserEntity.fromDomain(user)
        // 2) JPA save (UserEntity 반환)
        val savedEntity = userJpaRepository.save(entity)
        // 3) JPA 엔티티 → 도메인 모델
        return savedEntity.toUser()
    }

    override fun findByUsername(username: String): User? {
        return userJpaRepository.findByUsername(username)?.toUser()
    }

    override fun existsByUsername(username: String): Boolean {
        return userJpaRepository.existsByUsername(username)
    }

    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }

    override fun findByOauth2(oauth2: Oauth2): User? {
        return userJpaRepository.findByOauth2(Oauth2Entity.fromDomain(oauth2))?.toUser()
    }

    override fun deleteOldUser(sevenDaysAgo: LocalDateTime) {
        return userJpaRepository.deleteOldUser(sevenDaysAgo)
    }

    override fun findByIdOrNull(id: String): User? = userJpaRepository.findByIdOrNull(id)?.toUser()

    override fun existsById(id: String): Boolean {
        return userJpaRepository.existsById(id)
    }
}