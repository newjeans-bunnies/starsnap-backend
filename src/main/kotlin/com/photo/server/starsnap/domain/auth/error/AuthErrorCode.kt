package com.photo.server.starsnap.domain.auth.error


import com.photo.server.starsnap.global.error.custom.CustomErrorProperty


enum class AuthErrorCode(
    private val status: Int,
    private val message: String
) : CustomErrorProperty {

    NOT_FOUND_USER(404, "존재하지 않는 유저"),
    INVALID_PASSWORD(409, "유효하지 않는 비밀번호입니다."),
    EXIST_EMAIL(409, "이미 등록된 메일입니다.");

    override fun status(): Int = status
    override fun message(): String = message
}