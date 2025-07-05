package com.photo.server.starsnap.exception.star.error.exception

import com.photo.server.starsnap.exception.global.error.custom.CustomException
import com.photo.server.starsnap.exception.star.error.StarErrorCode

object UnsupportedTypeValueException: CustomException(
    StarErrorCode.UNSUPPORTED_TYPE_VALUE
)