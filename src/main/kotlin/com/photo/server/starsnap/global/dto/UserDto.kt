package com.photo.server.starsnap.global.dto

import com.photo.server.starsnap.domain.auth.type.Oauth2
import com.photo.server.starsnap.domain.user.entity.UserEntity
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest

data class UserDto(
    val userId: String,
    val username: String,
    val nickname: String,
    val email: String,
    val profileImageUrl: String? = null,
    val authority: String,
    val followingCount: Int,
    val followerCount: Int
)

fun UserEntity.toUserDto() = UserDto(
    userId = id,
    username = username,
    email = email,
    profileImageUrl = profileImageUrl,
    authority = authority.name,
    followerCount = followerCount,
    followingCount = followingCount,
    nickname = nickname
)

fun OAuth2UserRequest.of(registrationId: String, attributes: Map<String, Any>): OAuthDto {
    return when (registrationId) {
        "google" -> OAuthDto(Oauth2.GOOGLE, attributes["email"].toString(), attributes["name"].toString(), attributes["picture"].toString(), attributes["sub"].toString(), attributes["name"].toString())
        else -> throw RuntimeException("Unsupported OAuth2 user request")
    }
}