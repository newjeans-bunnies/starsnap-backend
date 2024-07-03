package com.photo.server.starsnap.global.security.principle

import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.global.error.exception.InvalidTokenException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(userId: String): UserDetails {
        val user = userRepository.findByIdOrNull(userId) ?: throw InvalidTokenException
        return CustomUserDetails(user.id, user.authority)
    }

}