package com.photo.server.starsnap.domain.snap.error.exception

import com.photo.server.starsnap.domain.snap.error.SnapErrorCode
import com.photo.server.starsnap.global.error.custom.CustomException

object NotFoundSnapIdException: CustomException(
    SnapErrorCode.NOT_FOUND_SNAP_ID
)