package com.photo.server.starsnap.usecase.auth.dto

data class EmailDto(
    val email: String,
    val verifyCode: String
)


data class EmailResponseDto(
    val email: String, val token: String
)
