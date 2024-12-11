package com.photo.server.starsnap.domain.auth.error


import com.photo.server.starsnap.global.error.custom.CustomErrorProperty


enum class AuthErrorCode(
    private val status: Int,
    private val message: String
) : CustomErrorProperty {
    UNSUPPORTED_USER_ROLLBACK(403, "Oauth계정은 Oauth Rollback을 이용해주세요."),
    ACCOUNT_NOT_SUSPENDED(400, "중지 되어 있지 않은 계정입니다."),
    PASSWORD_ALREADY_SET(409, "이미 비밀번호가 설정되어 있음"),
    UNSUPPORTED_LOGIN(403, "Oauth계정은 Oauth로그인을 이용해주세요."),
    NOT_FOUND_USER(404, "존재하지 않는 유저"),
    INVALID_PASSWORD(409, "유효하지 않는 비밀번호입니다."),
    EXIST_EMAIL(409, "이미 등록된 메일입니다.");

    override fun status(): Int = status
    override fun message(): String = message
}