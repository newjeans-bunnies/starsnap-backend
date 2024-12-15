package com.photo.server.starsnap.domain.auth.error.exception

import com.photo.server.starsnap.domain.auth.error.AuthErrorCode
import com.photo.server.starsnap.global.error.custom.CustomException

object UnsupportedOauthTypeException: CustomException(
    AuthErrorCode.UNSUPPORTED_OAUTH_TYPE,
)