package com.photo.server.starsnap.domain.report.dto

import com.photo.server.starsnap.domain.report.entity.SnapReportEntity
import com.photo.server.starsnap.domain.report.entity.UserReportEntity
import com.photo.server.starsnap.global.dto.SnapReportDto
import com.photo.server.starsnap.global.dto.UserReportDto
import com.photo.server.starsnap.global.dto.toSnapDto
import com.photo.server.starsnap.global.dto.toUserDto
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.data.domain.Slice

data class SnapReportCreateDto(
    @field:NotBlank(message = "설정은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^[\\p{IsHangul}a-zA-Z0-9 ]{1,50}\$", message = "1~50자 영어 대소문자, 한글, 띄어쓰기, 숫자")
    val explanation: String,
    @field:NotBlank(message = "snap Id는 필수 입력 값입니다.")
    @field:Pattern(regexp = "^[0-9a-zA-Z]{16}$", message = "16자 영문 대 소문자, 숫자, -,_")
    val snapId: String
)

data class UserReportCreateDto(
    @field:NotBlank(message = "설정은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^[\\p{IsHangul}a-zA-Z0-9 ]{1,50}\$", message = "1~50자 영어 대소문자, 한글, 띄어쓰기, 숫자")
    val explanation: String,
    @field:NotBlank(message = "snap Id는 필수 입력 값입니다.")
    @field:Pattern(regexp = "^[\\-_0-9a-zA-Z]{16}$", message = "16자 영문 대 소문자, 숫자, -,_")
    val userId: String
)

fun Slice<SnapReportEntity>.toSnapReportDto(): Slice<SnapReportDto> = this.map {
    SnapReportDto(
        id = it.id,
        createdAt = it.createdAt,
        explanation = it.explanation,
        reporter = it.reporter.toUserDto(),
        snap = it.snap.toSnapDto()
    )
}

fun Slice<UserReportEntity>.toUserReportDto(): Slice<UserReportDto> = this.map {
    UserReportDto(
        id = it.id,
        createdAt = it.createdAt,
        explanation = it.explanation,
        reporter = it.reporter.toUserDto(),
        defendant = it.defendant.toUserDto()
    )
}