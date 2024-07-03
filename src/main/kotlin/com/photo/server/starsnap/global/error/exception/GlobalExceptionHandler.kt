package com.photo.server.starsnap.global.error.exception

import com.photo.server.starsnap.global.error.custom.CustomException
import com.photo.server.starsnap.global.error.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.multipart.MaxUploadSizeExceededException


@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleEmptyResultDataAccessException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorMessage: String? = ex.bindingResult
            .allErrors[0]
            .defaultMessage
        return ResponseEntity(
            ErrorResponse(
                400,
                errorMessage
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxUploadSizeExceededException(ex: MaxUploadSizeExceededException): ResponseEntity<ErrorResponse> {
        val errorMessage: String? = ex.message
        return ResponseEntity(
            ErrorResponse(
                400,
                errorMessage ?: ""
            ), HttpStatus.BAD_REQUEST
        )
    }


    @ExceptionHandler(CustomException::class)
    protected fun handleBindException(ex: CustomException): ResponseEntity<ErrorResponse> {
        val httpStatus = when (ex.errorProperty.status()) {
            204 -> HttpStatus.NO_CONTENT
            400 -> HttpStatus.BAD_REQUEST
            401 -> HttpStatus.UNAUTHORIZED
            403 -> HttpStatus.FORBIDDEN
            404 -> HttpStatus.NOT_FOUND
            409 -> HttpStatus.CONFLICT
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        return ResponseEntity(
            ErrorResponse(
                status = ex.errorProperty.status(),
                message = ex.errorProperty.message()
            ), httpStatus
        )
    }
}