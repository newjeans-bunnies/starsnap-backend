package com.photo.server.starsnap.exception.file.error

import com.photo.server.starsnap.exception.global.error.custom.CustomErrorProperty


enum class FileErrorCode(
    private val status: Int,
    private val message: String
) : CustomErrorProperty {
    UNSUPPORTED_FILE_TYPE(415, "지원하지 않는 확장자 파일"),
    PHOTO_ALREADY_LINKED(409, "이미 연결된 이미지입니다."),
    NOT_FOUND_PHOTO_ID(404, "존재 하지 않는 photo id 입니다."),
    NOT_FOUND_VIDEO_ID(404, "존재 하지 않는 video id 입니다.");

    override fun status(): Int = status
    override fun message(): String = message
}