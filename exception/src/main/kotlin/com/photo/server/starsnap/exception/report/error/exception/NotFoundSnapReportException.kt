package com.photo.server.starsnap.exception.report.error.exception

import com.photo.server.starsnap.exception.global.error.custom.CustomException
import com.photo.server.starsnap.exception.report.error.ReportErrorCode

object NotFoundSnapReportException: CustomException(
    ReportErrorCode.NOT_FOUND_SNAP_REPORT
)