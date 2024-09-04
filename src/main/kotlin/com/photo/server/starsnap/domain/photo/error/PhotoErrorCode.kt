package com.photo.server.starsnap.domain.photo.error

import com.photo.server.starsnap.global.error.custom.CustomErrorProperty

enum class PhotoErrorCode(
    private val status: Int,
    private val message: String
) : CustomErrorProperty {

    TEST(200, "OK");

    override fun status(): Int = status
    override fun message(): String = message
}