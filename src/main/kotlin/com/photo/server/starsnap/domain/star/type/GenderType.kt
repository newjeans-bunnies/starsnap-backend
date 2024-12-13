package com.photo.server.starsnap.domain.star.type

import com.photo.server.starsnap.domain.auth.error.exception.UnsupportedGenderTypeException


enum class GenderType {
    MALE, FEMALE
}

fun String.toGenderType() = try {
    enumValueOf<GenderType>(this.uppercase())
} catch (e: IllegalArgumentException) {
    throw UnsupportedGenderTypeException
}
