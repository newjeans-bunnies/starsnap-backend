package com.photo.server.starsnap.exception.global.error.exception

import com.photo.server.starsnap.exception.global.error.GlobalErrorCode
import com.photo.server.starsnap.exception.global.error.custom.CustomException


object TooManyRequestException: CustomException(
    GlobalErrorCode.TOO_MANY_REQUEST
)