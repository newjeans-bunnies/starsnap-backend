package com.photo.server.starsnap.usecase.star.dto

import com.photo.server.starsnap.domain.star.entity.Star
import com.photo.server.starsnap.domain.star.type.GenderType
import java.time.LocalDateTime

data class CreateStarRequestDto(
    val name: String, // 이름
    val gender: GenderType, // 성별
    val birthday: LocalDateTime, // 생일
    val nickname: String, // 닉네임
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
    val id: String,
    val name: String, // 이름
    val gender: GenderType, // 성별
    val birthday: LocalDateTime, // 생일
    val nickname: String, // 닉네임
    val explanation: String? // 설명
)


data class StarImageResponseDto(
    val imageKey: String
)

fun Star.toDto(): StarResponseDto {
    return StarResponseDto(
        name = name,
        gender = gender,
        birthday = birthday,
        nickname = nickname,
        explanation = explanation
    )
}

fun CreateStarRequestDto.toEntity(): Star {
    return Star(
        name = this.name,
        gender = this.gender,
        birthday = this.birthday,
        explanation = this.explanation,
        nickname = this.nickname
    )
}