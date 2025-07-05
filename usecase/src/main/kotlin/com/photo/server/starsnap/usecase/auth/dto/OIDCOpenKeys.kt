package com.photo.server.starsnap.usecase.auth.dto

data class OIDCOpenKeys(
    val keys: List<Key>
)

data class Key(
    val kid: String,
    val kty: String,
    val alg: String,
    val use: String,
    val n: String,
    val e: String
)