package com.photo.server.starsnap.usecase.auth.dto

data class OIDCPublicKey(
    val kid: String,
    val alg: String,
    val use: String,
    val n: String,
    val e: String
)