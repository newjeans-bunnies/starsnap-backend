package com.photo.server.starsnap.domain.auth.error.exception

import com.photo.server.starsnap.domain.auth.error.AuthErrorCode
import com.photo.server.starsnap.global.error.custom.CustomException

object UnsupportedAuthorityTypeException: CustomException(
    AuthErrorCode.UNSUPPORTED_AUTHORITY_TYPE
)