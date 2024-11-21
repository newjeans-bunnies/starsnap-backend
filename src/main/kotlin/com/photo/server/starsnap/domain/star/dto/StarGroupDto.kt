package com.photo.server.starsnap.domain.star.dto

import com.photo.server.starsnap.domain.star.entity.StarGroupEntity
import com.photo.server.starsnap.domain.star.type.StarGroupType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime

data class CreateStarGroupDto(
    @field:NotBlank(message = "이름은 필수 입력 값입니다.")
    @field:Pattern(regexp = """^[가-힣a-zA-Z]+(\s[가-힣a-zA-Z]+)*\$""") // 한글, 영어 띄어쓰기 사용가능, 처음과 마지막에는 띄어쓰기 사용불가
    val name: String, // 이름
    @field:NotBlank(message = "데뷔일은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])\$") // yyyy-mm-dd
    val debutDate: LocalDateTime, // 데뷔일
    @field:Pattern(regexp = """^[가-힣a-zA-Z!@#$%^&*(),.?":{}|<>]+([\s\n][가-힣a-zA-Z!@#$%^&*(),.?":{}|<>]+)*$""") // 한글, 영어, 특수문자, 띄어쓰기, 엔터 사용가능, 처음과 마지막에는 엔터와 띄어쓰기 사용불가
    val explanation: String, // 설명
    @field:NotBlank(message = "그룹 타입은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^(BoyGroup|GirlGroup|CoedGroup)$") // BoyGroup or GirlGroup or CoedGroup 만 가능
    val starGroupType: StarGroupType // 그룹 타입
)

data class UpdateStarGroupDto(
    @field:NotBlank(message = "아이디는 필수 입력 값입니다.")
    val id: String,
    @field:NotBlank(message = "이름은 필수 입력 값입니다.")
    @field:Pattern(regexp = """^[가-힣a-zA-Z]+(\s[가-힣a-zA-Z]+)*\$""") // 한글, 영어 띄어쓰기 사용가능, 처음과 마지막에는 띄어쓰기 사용불가
    val name: String, // 이름
    @field:NotBlank(message = "데뷔일은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])\$") // yyyy-mm-dd
    val debutDate: LocalDateTime, // 데뷔일
    @field:Pattern(regexp = """^[가-힣a-zA-Z!@#$%^&*(),.?":{}|<>]+([\s\n][가-힣a-zA-Z!@#$%^&*(),.?":{}|<>]+)*$""") // 한글, 영어, 특수문자, 띄어쓰기, 엔터 사용가능, 처음과 마지막에는 엔터와 띄어쓰기 사용불가
    val explanation: String, // 설명
    @field:NotBlank(message = "그룹 타입은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^(BoyGroup|GirlGroup|CoedGroup)$") // BoyGroup or GirlGroup or CoedGroup 만 가능
    val starGroupType: StarGroupType // 그룹 타입
)

fun CreateStarGroupDto.toEntity(): StarGroupEntity {
    return StarGroupEntity(
        name = this.name,
        explanation = this.explanation,
        debutDate = this.debutDate
    )
}