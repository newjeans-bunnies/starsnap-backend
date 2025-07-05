package com.photo.server.starsnap.usecase.auth.dto

import com.photo.server.starsnap.domain.auth.type.Oauth2Type


data class OIDCDecodePayload(
    val iss: String,
    val aud: String,
    val sub: String,
    val email: String,
    val type: Oauth2Type,
    val nickname: String,
    val profileImageUrl: String
)