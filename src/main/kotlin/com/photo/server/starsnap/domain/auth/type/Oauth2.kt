package com.photo.server.starsnap.domain.auth.type

import com.photo.server.starsnap.domain.auth.error.exception.UnsupportedOauthTypeException

enum class Oauth2 {
    APPLE,
    GOOGLE
}

fun String.toOauth2() = try {
    enumValueOf<Oauth2>(this.uppercase())
} catch (e: IllegalArgumentException) {
    throw UnsupportedOauthTypeException
}

fun String.issToOauth2(): Oauth2{
    return when(this){
        "https://accounts.google.com" -> Oauth2.GOOGLE
        "https://accounts.google.com/" -> Oauth2.APPLE
        else -> throw UnsupportedOauthTypeException
    }
}