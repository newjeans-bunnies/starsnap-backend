package com.photo.server.starsnap.global.error


import com.photo.server.starsnap.global.error.custom.CustomErrorProperty


enum class GlobalErrorCode(
    private val status: Int,
    private val message: String
) : CustomErrorProperty {
    TOO_MANY_REQUEST(429, "너무 많은 요청을 보냈습니다."),
    NOT_EXIST_USER_ID(409, "존재 하지 않는 유저 아이디 입니다."),
    NOT_EXIST_USERNAME(409, "존재 하지 않는 닉네임 입니다."),
    EXIST_USER_ID(409, "유저 아이디가 이미 존재합니다"),
    EXIST_USERNAME(409, "닉네임이 이미 존재합니다"),
    INVALID_TOKEN(401,"잘못된 토큰"), //잘못된 토큰
    EXPIRED_TOKEN(401, "만료된 토큰"), //만료
    UNEXPECTED_TOKEN(401, "Unexpected Token"), //무단
    INVALID_ROLE(403, "권한 없음"), //권한 없음
    REFRESH_TOKEN_NOT_FOUND_EXCEPTION(400, "RefreshToken이 존재하지 않음"),
    INTERNAL_SERVER_ERROR(500, "서버 애러"), //서버 애러
    ;

    override fun status(): Int = status
    override fun message(): String = message
}