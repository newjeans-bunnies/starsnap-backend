package com.photo.server.starsnap.domain.star.error.exception

import com.photo.server.starsnap.domain.star.error.StarErrorCode
import com.photo.server.starsnap.global.error.custom.CustomException

object NotFoundFandomIdException: CustomException(
    StarErrorCode.NOT_FOUND_FANDOM_ID
)