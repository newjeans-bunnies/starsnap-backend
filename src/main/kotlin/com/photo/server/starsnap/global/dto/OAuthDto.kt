package com.photo.server.starsnap.global.dto

import com.photo.server.starsnap.domain.auth.type.Oauth2

data class OAuthDto(
    val type: Oauth2,
    val email: String,
    val name: String,
    val profileImage: String,
    val sub: String,
    val nickname: String
)