package com.photo.server.starsnap.domain.user.error

import com.photo.server.starsnap.global.error.custom.CustomErrorProperty

enum class UserErrorCode(
    private val status: Int,
    private val message: String
): CustomErrorProperty {


    SAME_USERNAME(409, "전에 사용중인 닉네임입니다.");

    override fun status(): Int = status
    override fun message(): String = message
}