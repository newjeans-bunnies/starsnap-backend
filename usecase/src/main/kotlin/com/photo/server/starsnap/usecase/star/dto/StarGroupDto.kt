package com.photo.server.starsnap.usecase.star.dto

import com.photo.server.starsnap.domain.star.entity.StarGroup
import com.photo.server.starsnap.domain.star.type.StarGroupType
import java.time.LocalDateTime


data class CreateStarGroupRequestDto(
    val name: String, // 이름
    val debutDate: LocalDateTime, // 데뷔일
    val explanation: String?, // 설명
    val starGroupType: StarGroupType // 그룹 타입
)

data class UpdateStarGroupRequestDto(
    val id: String,
    val name: String, // 이름
    val debutDate: LocalDateTime, // 데뷔일
    val explanation: String?, // 설명
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

fun StarGroup.toDto(): StarGroupResponseDto {
    return StarGroupResponseDto(
        name = this.name,
        debutDate = this.debutDate,
        explanation = this.explanation,
        starGroupType = this.starGroupType
    )
}

fun CreateStarGroupRequestDto.toEntity(): StarGroup {
    return StarGroup(
        name = this.name,
        explanation = this.explanation,
        debutDate = this.debutDate,
        starGroupType = this.starGroupType
    )
}