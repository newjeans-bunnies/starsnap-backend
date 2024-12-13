package com.photo.server.starsnap.domain.star.error.exception

import com.photo.server.starsnap.domain.star.error.StarErrorCode
import com.photo.server.starsnap.global.error.custom.CustomException

object NotFoundStarGroupIdException: CustomException(
    StarErrorCode.NOT_FOUND_STAR_GROUP_ID
)