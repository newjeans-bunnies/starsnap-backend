package com.photo.server.starsnap.domain.report.error.exception

import com.photo.server.starsnap.domain.report.error.ReportErrorCode
import com.photo.server.starsnap.global.error.custom.CustomException

object NotFoundCommentReportException: CustomException(
    ReportErrorCode.NOT_FOUND_COMMENT_REPORT
)