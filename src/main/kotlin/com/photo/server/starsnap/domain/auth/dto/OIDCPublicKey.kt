package com.photo.server.starsnap.domain.auth.dto

data class OIDCPublicKey(
    val kid: String,
    val alg: String,
    val use: String,
    val n: String,
    val e: String
)