package com.photo.server.starsnap.domain.report.error.exception

import com.photo.server.starsnap.domain.report.error.ReportErrorCode
import com.photo.server.starsnap.global.error.custom.CustomException

object NotFoundSnapReportException: CustomException(
    ReportErrorCode.NOT_FOUND_SNAP_REPORT
)