package com.photo.server.starsnap.exception.user.error.exception

import com.photo.server.starsnap.exception.global.error.custom.CustomException
import com.photo.server.starsnap.exception.user.error.UserErrorCode


object NotFoundUserIdException: CustomException(
    UserErrorCode.NOT_FOUND_USER_ID
)