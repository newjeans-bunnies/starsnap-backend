package com.photo.server.starsnap.domain.user.error.exception

import com.photo.server.starsnap.domain.user.error.UserErrorCode
import com.photo.server.starsnap.global.error.custom.CustomException

object NotFoundUserIdException: CustomException(
    UserErrorCode.NOT_FOUND_USER_ID
)