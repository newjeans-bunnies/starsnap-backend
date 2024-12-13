package com.photo.server.starsnap.domain.auth.type

import com.photo.server.starsnap.domain.auth.error.exception.UnsupportedAuthorityTypeException

enum class Authority {
    USER,
    ADMIN,
    STAR
}

fun String.toAuthority() = try {
    enumValueOf<Authority>(this.uppercase())
} catch (e: IllegalArgumentException){
    throw UnsupportedAuthorityTypeException
}