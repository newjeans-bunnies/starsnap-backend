package com.photo.server.starsnap.global.security.principle

import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.user.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomUserDetails(
    val userId: String,
    val user: UserEntity,
    val authority: Authority,
    private val oAuth2User: OAuth2User? = null
) : BaseCustomUserDetails, OAuth2User {
    override fun getName(): String = user.username

    override fun getAttributes(): MutableMap<String, Any> = oAuth2User?.attributes ?: mutableMapOf()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(authority.name))
    }

    override fun getPassword(): String? = null

    override fun getUsername(): String = userId

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun getUserData(): UserEntity = user
}

interface BaseCustomUserDetails : UserDetails {
    fun getUserData(): UserEntity
}