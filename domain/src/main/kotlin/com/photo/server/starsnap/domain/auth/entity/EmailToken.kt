package com.photo.server.starsnap.domain.auth.entity

data class EmailToken(
    val email: String,
    val token: String
)