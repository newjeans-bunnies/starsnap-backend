package com.photo.server.starsnap.exception.file.error.exception

import com.photo.server.starsnap.exception.file.error.FileErrorCode
import com.photo.server.starsnap.exception.global.error.custom.CustomException

object UnsupportedFileTypeException: CustomException(
    FileErrorCode.UNSUPPORTED_FILE_TYPE
)