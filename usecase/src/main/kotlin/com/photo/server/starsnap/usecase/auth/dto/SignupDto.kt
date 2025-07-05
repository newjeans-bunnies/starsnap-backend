package com.photo.server.starsnap.usecase.auth.dto

data class SignupDto(
    val username: String,
    val password: String,
    val email: String,
    val token: String
)