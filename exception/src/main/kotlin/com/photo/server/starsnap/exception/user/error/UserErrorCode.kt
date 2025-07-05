package com.photo.server.starsnap.exception.user.error

import com.photo.server.starsnap.exception.global.error.custom.CustomErrorProperty

enum class UserErrorCode(
    private val status: Int,
    private val message: String
): CustomErrorProperty {
    NOT_FOUND_FOLLOW_ID(404, "존재 하지 않는 FollowId"),
    NOT_FOUND_USER_ID(404, "존재 하지 않는 UserId");

    override fun status(): Int = status
    override fun message(): String = message
}