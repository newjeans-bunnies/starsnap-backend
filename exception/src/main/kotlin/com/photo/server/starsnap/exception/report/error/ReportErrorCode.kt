package com.photo.server.starsnap.exception.report.error

import com.photo.server.starsnap.exception.global.error.custom.CustomErrorProperty

enum class ReportErrorCode(
    private val status: Int,
    private val message: String
) : CustomErrorProperty {
    NOT_FOUND_SNAP_REPORT(404, "존재 하지 않는 SnapReport"),
    NOT_FOUND_USER_REPORT(404, "존재 하지 않는 UserReport"),
    NOT_FOUND_COMMENT_REPORT(404, "존재 하지 않는 CommentReport");
    override fun status(): Int = status
    override fun message(): String = message
}