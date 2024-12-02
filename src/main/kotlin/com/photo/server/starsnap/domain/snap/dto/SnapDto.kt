package com.photo.server.starsnap.domain.snap.dto

import com.photo.server.starsnap.global.dto.SnapDto
import com.photo.server.starsnap.global.dto.SnapUserDto

data class SnapResponseDto(
    val createdUser: SnapUserDto,
    val snapData: SnapDto
)