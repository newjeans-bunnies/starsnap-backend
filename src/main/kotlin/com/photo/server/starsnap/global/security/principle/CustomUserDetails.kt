package com.photo.server.starsnap.global.security.principle

import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.user.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val userId: String, private val authority: Authority, private val userData: UserEntity
) : BaseCustomUserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(authority.name))
    }

    override fun getPassword(): String? = null

    override fun getUsername(): String = userId

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun getUserData(): UserEntity = userData
}

interface BaseCustomUserDetails: UserDetails {
    fun getUserData(): UserEntity

}