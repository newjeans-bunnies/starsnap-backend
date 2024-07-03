package com.photo.server.starsnap.global.error.exception

import com.photo.server.starsnap.global.error.GlobalErrorCode
import com.photo.server.starsnap.global.error.custom.CustomException

object RefreshTokenNotForundException: CustomException(
    GlobalErrorCode.REFRESH_TOKEN_NOT_FOUND_EXCEPTION
)