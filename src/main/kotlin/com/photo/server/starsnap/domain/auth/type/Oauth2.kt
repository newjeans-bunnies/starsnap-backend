package com.photo.server.starsnap.domain.auth.type

enum class Oauth2 {
    APPLE,
    GOOGLE
}

fun String.toOauth2() = try {
    enumValueOf<Oauth2>(this.uppercase())
} catch (e: IllegalArgumentException) {
    throw RuntimeException("존재하지 않는 type")
}

fun String.issToOauth2(): Oauth2{
    return when(this){
        "https://accounts.google.com" -> Oauth2.GOOGLE
        "https://accounts.google.com/" -> Oauth2.APPLE
        else -> throw RuntimeException("없는데")
    }
}