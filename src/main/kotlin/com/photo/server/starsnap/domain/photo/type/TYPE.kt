package com.photo.server.starsnap.domain.photo.type

enum class TYPE {
    PNG,
    JPEG,
    JPG;
}

fun String?.toType() = when (this) {
    "image/jpeg" -> TYPE.JPEG
    "image/png" -> TYPE.PNG
    "image/jpg" -> TYPE.JPG
    else -> throw TODO("파입 애러")
}