package com.photo.server.starsnap.domain.file.type

import com.photo.server.starsnap.exception.global.error.exception.ExtensionNotSupportedException

enum class PhotoType {
    HEIF,
    HEIC,
    GIF,
    PNG,
    JPEG,
    JPG;
}

enum class VideoType {
    MP4,
}

fun String?.isPhotoType(): Boolean {
    return when (this) {
        "image/jpeg", "image/jpg", "image/png", "image/gif", "image/heif", "image/heic" -> true
        else -> false
    }
}
fun String?.toVideoType() = when (this) {
    "video/mp4" -> VideoType.MP4
    else -> throw ExtensionNotSupportedException
}