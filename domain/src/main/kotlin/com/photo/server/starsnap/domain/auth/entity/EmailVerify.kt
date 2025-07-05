package com.photo.server.starsnap.domain.auth.entity

data class EmailVerify(
    val email: String,
    val verifyCode: String
)