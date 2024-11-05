package com.photo.server.starsnap.domain.auth.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class SignupDto(
    @field:NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "닉네임은 4~12자 영문 대 소문자, 숫자만 사용하세요.")
    val username: String,
    @field:NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\d)(?=.*[~․!@#\$%^&*()_\\-+=|\\\\;:‘“<>,.?/]).{8,50}\$",
        message = "비밀번호는 8~50자 영문 대 소문자, 숫자, 특수문자를 사용하세요."
    )
    val password: String,
    @field:NotBlank(message = "이메일은 필수 입력 값입니다.")
    @field:Pattern(
        regexp = "^[a-zA-Z0-9+-_.]+@[0-9a-zA-Z]+\\.[a-zA-Z]{2,3}\$",
        message = "이메일을 적어주세요 ex) test@test.com"
    )
    val email: String,
    @field:NotBlank(message = "token은 필수 입력 값입니다.")
    val token: String
)