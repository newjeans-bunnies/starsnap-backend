package com.photo.server.starsnap.exception.auth.error.exception

import com.photo.server.starsnap.exception.auth.error.AuthErrorCode
import com.photo.server.starsnap.exception.global.error.custom.CustomException

object UnsupportedGenderTypeException: CustomException(
    AuthErrorCode.UNSUPPORTED_GENDER_TYPE,
)