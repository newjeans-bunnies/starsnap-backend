package com.photo.server.starsnap.domain.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OIDCOpenKeys(
    @JsonProperty("keys")
    val keys: List<Key>
)

data class Key(
    @JsonProperty("kid")
    val kid: String,
    @JsonProperty("kty")
    val kty: String,
    @JsonProperty("alg")
    val alg: String,
    @JsonProperty("use")
    val use: String,
    @JsonProperty("n")
    val n: String,
    @JsonProperty("e")
    val e: String
)