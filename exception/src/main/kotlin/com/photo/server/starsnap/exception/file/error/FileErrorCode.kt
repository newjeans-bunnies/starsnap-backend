package com.photo.server.starsnap.exception.file.error

import com.photo.server.starsnap.exception.global.error.custom.CustomErrorProperty


enum class FileErrorCode(
    private val status: Int,
    private val message: String
) : CustomErrorProperty {
    UNSUPPORTED_FILE_TYPE(415, "지원하지 않는 확장자 파일"),
    UNSUPPORTED_AUTHORITY_TYPE(400, "지원 하지 않는 권한"),
    UNSUPPORTED_GENDER_TYPE(400, "지원 하지 않는 권한"),
    INVALID_VERIFICATION_CODE(400, "잘못된 인증코드"),
    NOT_FOUND_EMAIL(409, "존재하지 않는 emil"),
    NOT_FOUND_OAUTH_ACCOUNT(409, "존재하지 않는 Oauth 계정"),
    UNSUPPORTED_OAUTH_TYPE(403, "지원하지 않는 Oauth type"),
    UNSUPPORTED_USER_ROLLBACK(403, "Oauth계정은 Oauth Rollback을 이용해주세요."),
    ACCOUNT_NOT_SUSPENDED(400, "중지 되어 있지 않은 계정입니다."),
    PASSWORD_ALREADY_SET(409, "이미 비밀번호가 설정되어 있음"),
    UNSUPPORTED_LOGIN(403, "Oauth계정은 Oauth로그인을 이용해주세요."),
    NOT_FOUND_USER(404, "존재하지 않는 유저"),
    INVALID_PASSWORD(409, "유효하지 않는 비밀번호입니다."),
    INVALID_USERNAME_FORMAT(400, ""),
    INVALID_EMAIL_FORMAT(400, ""),
    NOT_FOUND_PHOTO_ID(404, "존재 하지 않는 photo id 입니다."),
    NOT_FOUND_VIDEO_ID(404, "존재 하지 않는 video id 입니다."),
    EXIST_USERNAME(409, "이미 등록된 닉네임입니다."),
    EXIST_EMAIL(409, "이미 등록된 메일입니다.");

    override fun status(): Int = status
    override fun message(): String = message
}