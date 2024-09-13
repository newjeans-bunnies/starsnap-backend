package com.photo.server.starsnap.domain.user.controller.dto.request

data class ChangePasswordDto(
    val userId: String,
    val password: String,
    val newPassword: String
)
