package com.photo.server.starsnap.exception.user.error.exception

import com.photo.server.starsnap.exception.global.error.custom.CustomException
import com.photo.server.starsnap.exception.user.error.UserErrorCode


object NotFoundFollowIdException: CustomException(
    UserErrorCode.NOT_FOUND_FOLLOW_ID
)