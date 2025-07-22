package com.photo.server.starsnap.exception.file.error.exception

import com.photo.server.starsnap.exception.file.error.FileErrorCode
import com.photo.server.starsnap.exception.global.error.custom.CustomException

object NotFoundVideoIdException: CustomException(
    FileErrorCode.NOT_FOUND_VIDEO_ID
)