package com.photo.server.starsnap.domain.auth.error.exception

import com.photo.server.starsnap.domain.auth.error.AuthErrorCode
import com.photo.server.starsnap.global.error.custom.CustomException

object PasswordAlreadySetException: CustomException(
    AuthErrorCode.PASSWORD_ALREADY_SET,
)