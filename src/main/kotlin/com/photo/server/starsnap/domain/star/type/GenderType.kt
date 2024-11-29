package com.photo.server.starsnap.domain.star.type


enum class GenderType {
    MALE, FEMALE
}

fun String.toGenderType() = try {
    enumValueOf<GenderType>(this.uppercase())
} catch (e: IllegalArgumentException) {
    throw RuntimeException("존재하지 않는 type")
}
