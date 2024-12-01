package com.photo.server.starsnap.domain.star.dto

import com.photo.server.starsnap.domain.star.entity.StarGroupEntity
import com.photo.server.starsnap.domain.star.type.StarGroupType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime

data class CreateStarGroupRequestDto(
    @field:NotBlank(message = "이름은 필수 입력 값입니다.")
    @field:Pattern(
        regexp = """^[가-힣a-zA-Z]+(\s[가-힣a-zA-Z]+)*\$""",
        message = "한글, 영어 띄어쓰기 사용가능, 처음과 마지막에는 띄어쓰기 사용불가"
    ) // 한글, 영어 띄어쓰기 사용가능, 처음과 마지막에는 띄어쓰기 사용불가
    val name: String, // 이름
    @field:NotBlank(message = "데뷔일은 필수 입력 값입니다.")
    @field:Pattern(
        regexp = "^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])\$",
        message = "yyyy-mm-dd"
    ) // yyyy-mm-dd
    val debutDate: LocalDateTime, // 데뷔일
    @field:Pattern(
        regexp = """^[가-힣a-zA-Z!@#$%^&*(),.?":{}|<>]+([\s\n][가-힣a-zA-Z!@#$%^&*(),.?":{}|<>]+)*$""",
        message = "한글, 영어, 특수문자, 띄어쓰기, 엔터 사용가능, 처음과 마지막에는 엔터와 띄어쓰기 사용불가"
    ) // 한글, 영어, 특수문자, 띄어쓰기, 엔터 사용가능, 처음과 마지막에는 엔터와 띄어쓰기 사용불가
    val explanation: String?, // 설명
    @field:NotBlank(message = "그룹 타입은 필수 입력 값입니다.")
    @field:Pattern(
        regexp = "^(BoyGroup|GirlGroup|CoedGroup)$",
        message = "only BoyGroup, GirlGroup, CoedGroup"
    ) // BoyGroup or GirlGroup or CoedGroup 만 가능
    val starGroupType: StarGroupType // 그룹 타입
)

data class UpdateStarGroupRequestDto(
    @field:NotBlank(message = "아이디는 필수 입력 값입니다.")
    @field:Pattern(regexp = """^[\-_0-9a-zA-Z]{16}$""", message = "한글, 대소문자만 사용가능")
    val id: String,
    @field:NotBlank(message = "이름은 필수 입력 값입니다.")
    @field:Pattern(
        regexp = """^[가-힣a-zA-Z]+(\s[가-힣a-zA-Z]+)*\$""",
        message = "한글, 영어 띄어쓰기 사용가능, 처음과 마지막에는 띄어쓰기 사용불가"
    ) // 한글, 영어 띄어쓰기 사용가능, 처음과 마지막에는 띄어쓰기 사용불가
    val name: String, // 이름
    @field:NotBlank(message = "데뷔일은 필수 입력 값입니다.")
    @field:Pattern(
        regexp = "^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])\$",
        message = ""
    ) // yyyy-mm-dd
    val debutDate: LocalDateTime, // 데뷔일
    @field:Pattern(
        regexp = """^[가-힣a-zA-Z!@#$%^&*(),.?":{}|<>]+([\s\n][가-힣a-zA-Z!@#$%^&*(),.?":{}|<>]+)*$""",
        message = "한글, 영어, 특수문자, 띄어쓰기, 엔터 사용가능, 처음과 마지막에는 엔터와 띄어쓰기 사용불가"
    ) // 한글, 영어, 특수문자, 띄어쓰기, 엔터 사용가능, 처음과 마지막에는 엔터와 띄어쓰기 사용불가
    val explanation: String?, // 설명
    @field:NotBlank(message = "그룹 타입은 필수 입력 값입니다.")
    @field:Pattern(
        regexp = "^(BoyGroup|GirlGroup|CoedGroup)$",
        message = "only BoyGroup, GirlGroup, CoedGroup"
    ) // BoyGroup or GirlGroup or CoedGroup 만 가능
    val starGroupType: StarGroupType? // 그룹 타입
)

data class StarGroupResponseDto(
    val name: String,
    val debutDate: LocalDateTime,
    val explanation: String?,
    val starGroupType: StarGroupType
)


data class StarGroupImageResponseDto(
    val imageKey: String
)

fun StarGroupEntity.toDto(): StarGroupResponseDto {
    return StarGroupResponseDto(
        name = this.name,
        debutDate = this.debutDate,
        explanation = this.explanation,
        starGroupType = this.starGroupType
    )
}

fun CreateStarGroupRequestDto.toEntity(): StarGroupEntity {
    return StarGroupEntity(
        name = this.name,
        explanation = this.explanation,
        debutDate = this.debutDate,
        starGroupType = this.starGroupType
    )
}