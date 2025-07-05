package com.photo.server.starsnap.adapter_infrastructure.global.security.principle

import com.photo.server.starsnap.adapter_infrastructure.user.repository.UserRepositoryImpl
import com.photo.server.starsnap.exception.global.error.exception.InvalidTokenException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsUseCaseImpl(
    private val userRepositoryImpl: UserRepositoryImpl
) : UserDetailsService {
    override fun loadUserByUsername(userId: String): UserDetails {
        val user = userRepositoryImpl.findByIdOrNull(userId) ?: throw InvalidTokenException
        return CustomUserDetails(user.id, user, user.authority)
    }
}