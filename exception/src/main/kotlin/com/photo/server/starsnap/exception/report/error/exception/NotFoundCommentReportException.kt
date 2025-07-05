package com.photo.server.starsnap.exception.report.error.exception

import com.photo.server.starsnap.exception.global.error.custom.CustomException
import com.photo.server.starsnap.exception.report.error.ReportErrorCode

object NotFoundCommentReportException: CustomException(
    ReportErrorCode.NOT_FOUND_COMMENT_REPORT
)