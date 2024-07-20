package com.photo.server.starsnap.global.error.exception

import com.photo.server.starsnap.global.error.GlobalErrorCode
import com.photo.server.starsnap.global.error.custom.CustomException

object InternalServerErrorException: CustomException(
    GlobalErrorCode.INTERNAL_SERVER_ERROR
)