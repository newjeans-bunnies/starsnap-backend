package com.photo.server.starsnap.domain.report.error

import com.photo.server.starsnap.global.error.custom.CustomErrorProperty

enum class ReportErrorCode(
    private val status: Int,
    private val message: String
) : CustomErrorProperty {
    NOT_FOUND_SNAP_REPORT(404, "존재 하지 않는 SnapReport"),
    NOT_FOUND_USER_REPORT(404, "존재 하지 않는 UserReport");
    override fun status(): Int = status
    override fun message(): String = message
}