package com.photo.server.starsnap.exception.snap.error

import com.photo.server.starsnap.exception.global.error.custom.CustomErrorProperty

enum class SnapErrorCode(
    private val status: Int,
    private val message: String
) : CustomErrorProperty {
    NOT_FOUND_TAG(404, "존재 하지 않는 tag"),
    NOT_FOUND_SNAP_ID(404, "존재 하지 않는 SnapId"),
    NOT_FOUND_SNAP(404, "존재 하지 않는 Snap"),
    NOT_FOUND_COMMENT_ID(404, "존재 하지 않는 CommentId");

    override fun status(): Int = status
    override fun message(): String = message
}