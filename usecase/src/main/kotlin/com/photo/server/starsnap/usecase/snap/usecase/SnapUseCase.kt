package com.photo.server.starsnap.usecase.snap.usecase

import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.snap.dto.*


interface SnapUseCase {
    fun createSnap(
        userData: User,
        snapDto: CreateSnapRequestDto
    ): CreateSnapResponseDto

    fun updateSnap(
        snapDto: UpdateSnapRequestDto,
        user: User
    ): SnapResponseDto

    fun deleteSnap(user: User, snapId: String?)

    fun getSnap(getSnapResponseDto: GetSnapResponseDto, user: User): Slice<SnapResponseDto>


    fun getAllSnap(): List<SnapResponseDto>
}
