package com.photo.server.starsnap.domain.auth.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class EmailDto(
    @field:NotBlank(message = "Email can't be blank.") @field:Pattern(
        regexp = "^[a-zA-Z0-9+-_.]+@[0-9a-zA-Z]+\\.[a-zA-Z]{2,3}\$",
        message = "only email"
    ) val email: String, @field:NotBlank(message = "VerifyCode can't be blank.") @field:Pattern(
        regexp = "^\\d{6}\$", message = "Verify Code can only be 6 characters long"
    ) val verifyCode: String
)


data class EmailResponseDto(
    @field:NotBlank(message = "Email can't be blank.") @field:Pattern(
        regexp = "^[a-zA-Z0-9+-_.]+@[0-9a-zA-Z]+\\.[a-zA-Z]{2,3}\$",
        message = "only email"
    ) val email: String, @field:NotBlank(message = "인증번호는 필수 입력 값입니다.") @field:Pattern(
        regexp = "^\\d{6}\$", message = "인증번호는 숫자 6글자만 가능합니다"
    ) @field:NotBlank(message = "VerifyCode can't be blank.") @field:Pattern(
        regexp = "^\\d{6}\$", message = "Verify Code can only be 6 characters long"
    ) val token: String
)
