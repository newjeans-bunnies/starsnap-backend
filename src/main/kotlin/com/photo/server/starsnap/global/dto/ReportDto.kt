package com.photo.server.starsnap.global.dto

data class UserReportDto(
    val id: String,
    val createdAt: String,
    var explanation: String,
    val reporter: UserDto,
    val defendant: UserDto
)

data class SnapReportDto(
    val id: String,
    val createdAt: String,
    var explanation: String,
    val reporter: UserDto,
    val snap: SnapDto
)
