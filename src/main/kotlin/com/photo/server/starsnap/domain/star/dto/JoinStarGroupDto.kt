package com.photo.server.starsnap.domain.star.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class JoinStarGroupDto(
    @field:NotBlank(message = "아이돌 아이디는 필수 입력 값입니다.")
    @field:Pattern(regexp = """^[\-_0-9a-zA-Z]{16}$""")
    val starId: String,
    @field:NotBlank(message = "그룹 아이디는 필수 입력 값입니다.")
    @field:Pattern(regexp = """^[\-_0-9a-zA-Z]{16}$""")
    val starGroupId: String
)