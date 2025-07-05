package com.photo.server.starsnap.exception.snap.error.exception

import com.photo.server.starsnap.exception.global.error.custom.CustomException
import com.photo.server.starsnap.exception.snap.error.SnapErrorCode


object NotFoundSnapException: CustomException(
    SnapErrorCode.NOT_FOUND_SNAP
)