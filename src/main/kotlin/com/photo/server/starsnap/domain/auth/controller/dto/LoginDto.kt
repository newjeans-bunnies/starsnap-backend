package com.photo.server.starsnap.domain.auth.controller.dto

import jakarta.validation.constraints.NotBlank

data class LoginDto(
    @field:NotBlank(message = "닉네임은 필수 입력 값입니다.")
    val username: String,
    @field:NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    val password: String
)
