package com.photo.server.starsnap.domain.user.error.exception

import com.photo.server.starsnap.domain.user.error.UserErrorCode
import com.photo.server.starsnap.global.error.custom.CustomException

object NotFoundFollowIdException: CustomException(
    UserErrorCode.NOT_FOUND_FOLLOW_ID
)