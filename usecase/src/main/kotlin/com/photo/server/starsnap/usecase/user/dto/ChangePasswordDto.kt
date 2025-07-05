package com.photo.server.starsnap.usecase.user.dto


data class ChangePasswordDto(
    val username: String,
    val password: String,
    val newPassword: String
)
