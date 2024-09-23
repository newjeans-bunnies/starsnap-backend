package com.photo.server.starsnap.global.utils.type

enum class TYPE {
    PNG,
    JPEG,
    JPG;
}

fun String?.toType() = when (this) {
    "image/jpeg" -> TYPE.JPEG
    "image/png" -> TYPE.PNG
    "image/jpg" -> TYPE.JPG
    else -> throw TODO("타입 애러")
}

fun String?.isValid(): Boolean {
    return when(this){
        "JPEG", "JPG", "PNG" -> false
        else -> true
    }
}