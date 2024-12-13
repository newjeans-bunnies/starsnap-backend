package com.photo.server.starsnap.global.utils.type

import com.photo.server.starsnap.global.error.exception.ExtensionNotSupportedException

enum class TYPE {
    PNG,
    JPEG,
    JPG;
}

fun String?.toType() = when (this) {
    "image/jpeg" -> TYPE.JPEG
    "image/png" -> TYPE.PNG
    "image/jpg" -> TYPE.JPG
    else -> throw ExtensionNotSupportedException
}

fun String?.isValid(): Boolean {
    return when(this){
        "JPEG", "JPG", "PNG" -> false
        else -> true
    }
}