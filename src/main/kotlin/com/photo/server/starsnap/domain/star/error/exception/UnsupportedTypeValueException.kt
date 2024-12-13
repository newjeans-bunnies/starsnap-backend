package com.photo.server.starsnap.domain.star.error.exception

import com.photo.server.starsnap.domain.star.error.StarErrorCode
import com.photo.server.starsnap.global.error.custom.CustomException

object UnsupportedTypeValueException: CustomException(
    StarErrorCode.UNSUPPORTED_TYPE_VALUE
)