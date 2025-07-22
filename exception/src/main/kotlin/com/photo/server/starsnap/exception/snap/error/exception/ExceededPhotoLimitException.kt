package com.photo.server.starsnap.exception.snap.error.exception

import com.photo.server.starsnap.exception.global.error.custom.CustomException
import com.photo.server.starsnap.exception.snap.error.SnapErrorCode

object ExceededPhotoLimitException: CustomException(
    SnapErrorCode.EXCEEDED_PHOTO_LIMIT
)