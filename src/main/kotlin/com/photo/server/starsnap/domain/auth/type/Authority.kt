package com.photo.server.starsnap.domain.auth.type

enum class Authority {
    USER,
    ADMIN,
    STAR
}

fun String.toAuthority() = try {
    enumValueOf<Authority>(this.uppercase())
} catch (e: IllegalArgumentException){
    throw RuntimeException("존재하지 않는 type")
}