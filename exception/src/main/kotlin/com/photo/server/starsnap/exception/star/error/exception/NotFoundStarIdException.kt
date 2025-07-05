package com.photo.server.starsnap.exception.star.error.exception

import com.photo.server.starsnap.exception.global.error.custom.CustomException
import com.photo.server.starsnap.exception.star.error.StarErrorCode

object NotFoundStarIdException: CustomException(
    StarErrorCode.NOT_FOUND_STAR_ID
)