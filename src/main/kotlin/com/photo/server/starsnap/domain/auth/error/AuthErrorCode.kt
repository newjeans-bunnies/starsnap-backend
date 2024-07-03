package com.photo.server.starsnap.domain.auth.error


import com.photo.server.starsnap.global.error.custom.CustomErrorProperty


enum class AuthErrorCode(
    private val status: Int,
    private val message: String
) : CustomErrorProperty {

    EXIST_USER_ID_EXCEPTION(409, "아이디가 이미 존재합니다."),
    INVALID_PASSWORD_EXCEPTION(409, "유효하지 않는 비밀번호입니다."),
    NOT_EXIST_ID(409, "존재하지 않는 아이디입니다."),
    EXIST_PHONE_EXCEPTION(409, "이미 등록된 전화번호입니다.")
    ;

    override fun status(): Int = status
    override fun message(): String = message
}