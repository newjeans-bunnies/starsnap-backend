package com.photo.server.starsnap.exception.star.error

import com.photo.server.starsnap.exception.global.error.custom.CustomErrorProperty

enum class StarErrorCode(
    private val status: Int,
    private val message: String
) : CustomErrorProperty {
    NOT_FOUND_STAR_GROUP_ID(404, "존재 하지 않는 StarGroupId"),
    NOT_FOUND_STAR_ID(404, "존재 하지 않는 StarId"),
    NOT_FOUND_FANDOM_ID(404, "존재 하지 않는 FandomId"),
    NOT_FOUND_FANDOM(404, "존재 하지 않는 Fandom"),
    NOT_FOUND_FANDOM_JOIN(404, "존재 하지 않는 Fandom Join"),
    NOT_CONNECTED_FAN(409, "이미 팬과 해제됨"),
    UNSUPPORTED_TYPE_VALUE(400, "지원하지 않는 type");

    override fun status(): Int = status
    override fun message(): String = message
}