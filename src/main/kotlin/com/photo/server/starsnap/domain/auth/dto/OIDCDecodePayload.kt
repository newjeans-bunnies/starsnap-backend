package com.photo.server.starsnap.domain.auth.dto

import com.photo.server.starsnap.domain.auth.type.Oauth2

data class OIDCDecodePayload(
    val iss: String,
    val aud: String,
    val sub: String,
    val email: String,
    val type: Oauth2,
    val nickname: String,
    val profileImageUrl: String
)