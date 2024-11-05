package com.photo.server.starsnap.domain.auth.dto

data class Oauth2LoginDto(
    val type: String,
    val token: String
)

data class Oauth2SignupDto(
    val type: String,
    val token: String,
    val username: String
)
