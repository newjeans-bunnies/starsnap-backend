package com.photo.server.starsnap.domain.star.dto

import com.photo.server.starsnap.domain.star.entity.StarEntity
import com.photo.server.starsnap.domain.star.type.GenderType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime

data class CreateStarRequestDto(
    @field:NotBlank(message = "이름은 필수 입력 값입니다.")
    @field:Pattern(regexp = """^[가-힣a-zA-Z]+(\s[가-힣a-zA-Z]+)*$""") // 한글, 영어 띄어쓰기 사용가능, 처음과 마지막에는 띄어쓰기 사용불가
    val name: String, // 이름
    @field:NotBlank(message = "성별은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^(남자|여자)$") // 남자 or 여자 만 가능
    val gender: GenderType, // 성별
    @field:NotBlank(message = "생일은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "yyyy-mm-dd 형식으로 작성") // yyyy-mm-dd
    val birthday: LocalDateTime, // 생일
    @field:NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @field:Pattern(regexp = """^[가-힣a-zA-Z]+(\s[가-힣a-zA-Z]+)*$""", message = "한글, 영어 띄어쓰기 사용가능") // 한글, 영어 띄어쓰기 사용가능, 처음과 마지막에는 띄어쓰기 사용불가
    val nickname: String, // 닉네임
    @field:Pattern(regexp = """^[가-힣a-zA-Z!@#$%^&*(),.?":{}|<>]+([\s\n][가-힣a-zA-Z!@#$%^&*(),.?":{}|<>]+)*$""") // 한글, 영어, 특수문자, 띄어쓰기, 엔터 사용가능, 처음과 마지막에는 엔터와 띄어쓰기 사용불가
    val explanation: String? // 설명
)

data class StarResponseDto(
    val name: String,
    val gender: GenderType,
    val birthday: LocalDateTime,
    val nickname: String,
    val explanation: String?
)

data class UpdateStarRequestDto(
    @field:NotBlank(message = "아이디는 필수 입력 값입니다.")
    @field:Pattern(regexp = """^[\-_0-9a-zA-Z]{16}$""", message = "16자 영어 대소문자, 숫자, -, _ 가능")
    val id: String,
    @field:NotBlank(message = "이름은 필수 입력 값입니다.")
    @field:Pattern(regexp = """^[가-힣a-zA-Z]+(\s[가-힣a-zA-Z]+)*\$""", message = "한글, 영어 띄어쓰기 사용가능") // 한글, 영어 띄어쓰기 사용가능, 처음과 마지막에는 띄어쓰기 사용불가
    val name: String, // 이름
    @field:NotBlank(message = "성별은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^(남자|여자)$", message = "남자 또는 여자만 가능합니다.") // 남자 or 여자 만 가능
    val gender: GenderType, // 성별
    @field:NotBlank(message = "생일은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])\$", message = "yyyy-mm-dd 형식으로 작성") // yyyy-mm-dd
    val birthday: LocalDateTime, // 생일
    @field:NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @field:Pattern(regexp = """^[가-힣a-zA-Z]+(\s[가-힣a-zA-Z]+)*\$""", message = "한글, 영어 띄어쓰기 사용가능") // 한글, 영어 띄어쓰기 사용가능, 처음과 마지막에는 띄어쓰기 사용불가
    val nickname: String, // 닉네임
    @field:Pattern(regexp = """^[가-힣a-zA-Z!@#$%^&*(),.?":{}|<>]+([\s\n][가-힣a-zA-Z!@#$%^&*(),.?":{}|<>]+)*$""", message = "한글, 영어, 특수문자, 띄어쓰기, 엔터 사용가능, 처음과 마지막에는 엔터와 띄어쓰기 사용불가") // 한글, 영어, 특수문자, 띄어쓰기, 엔터 사용가능, 처음과 마지막에는 엔터와 띄어쓰기 사용불가
    val explanation: String? // 설명
)


data class StarImageResponseDto(
    val imageKey: String
)

fun StarEntity.toDto(): StarResponseDto {
    return StarResponseDto(
        name = name,
        gender = gender,
        birthday = birthday,
        nickname = nickname,
        explanation = explanation
    )
}

fun CreateStarRequestDto.toEntity(): StarEntity {
    return StarEntity(
        name = this.name,
        gender = this.gender,
        birthday = this.birthday,
        explanation = this.explanation,
        nickname = this.nickname
    )
}