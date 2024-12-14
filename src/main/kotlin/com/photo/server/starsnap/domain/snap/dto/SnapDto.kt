package com.photo.server.starsnap.domain.snap.dto

import com.photo.server.starsnap.global.dto.SnapDto
import com.photo.server.starsnap.global.dto.SnapUserDto
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

data class SnapResponseDto(
    val createdUser: SnapUserDto,
    val snapData: SnapDto
)

data class CreateSnapRequestDto(
    @field:NotBlank(message = "사진은 필수 입력 값입니다.")
    val image: MultipartFile,
    @field:NotBlank(message = "제목은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^[\\p{L}\\p{N}\\p{P}\\p{Z}\\p{S}]{1,50}\$", message = "1~50자 영어 대소문자, 숫자, 한글, 띄어쓰기, 유니코드 사용가능")
    val title: String,
    @field:NotBlank(message = "출처는 필수 입력 값입니다.")
    @field:Pattern(regexp = "^[\\p{IsHangul}a-zA-Z0-9 ]{1,50}\$", message = "1~50자 영어 대소문자, 한글, 띄어쓰기, 숫자")
    val source: String,
    @field:NotBlank(message = "사진찍은 시간은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "YYYY-MM-dd 입력필요") // yyyy-mm-dd
    val dateToken: LocalDateTime,
    @field:NotBlank(message = "ai 상태는 필수 입력 값입니다.")
    val aiState: Boolean,
    @field:NotBlank(message = "태그는 필수 입력 값입니다.")
    val tags: List<String>
)

data class UpdateSnapRequestDto(
    @field:NotBlank(message = "snap Id는 필수 입력 값입니다.")
    @field:Pattern(regexp = "^[\\-_0-9a-zA-Z]{16}$", message = "16자 영문 대 소문자, 숫자, -,_")
    val snapId: String,
    @field:NotBlank(message = "사진은 필수 입력 값입니다.")
    val image: MultipartFile,
    @field:NotBlank(message = "제목은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^[\\p{L}\\p{N}\\p{P}\\p{Z}\\p{S}]{1,50}\$", message = "1~50자 영어 대소문자, 숫자, 한글, 띄어쓰기, 유니코드 사용가능")
    val title: String,
    @field:NotBlank(message = "출처는 필수 입력 값입니다.")
    @field:Pattern(regexp = "^[\\p{IsHangul}a-zA-Z0-9 ]{1,50}\$", message = "1~50자 영어 대소문자, 한글, 띄어쓰기, 숫자")
    val source: String,
    @field:NotBlank(message = "사진찍은 시간은 필수 입력 값입니다.")
    @field:Pattern(regexp = "^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "YYYY-MM-dd 입력필요") // yyyy-mm-dd
    val dateTaken: LocalDateTime,
    @field:NotBlank(message = "ai 상태는 필수 입력 값입니다.")
    val aiState: Boolean,
    @field:NotBlank(message = "태그는 필수 입력 값입니다.")
    val tags: List<String>
)