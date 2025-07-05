package com.photo.server.starsnap.usecase.auth.dto

import com.photo.server.starsnap.domain.auth.type.Oauth2Type

data class OAuthDto(
    val type: Oauth2Type,
    val email: String,
    val name: String,
    val profileImage: String,
    val sub: String,
    val nickname: String
)
