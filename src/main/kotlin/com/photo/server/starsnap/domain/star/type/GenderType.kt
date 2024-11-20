package com.photo.server.starsnap.domain.star.type


enum class GenderType {
    male, female

}

fun String.toGenderType() = try {
    enumValueOf<GenderType>(this.uppercase())
} catch (e: IllegalArgumentException) {
    throw RuntimeException("존재하지 않는 type")
}
