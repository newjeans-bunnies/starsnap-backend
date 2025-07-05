package com.photo.server.starsnap.usecase.star.dto

data class CreateFandomRequestDto(
    val name: String,
    val explanation: String,
    val starGroupId: String
)

data class UpdateFandomRequestDto(
    val fandomId: String,
    val name: String,
    val explanation: String
)

data class FandomDto(
    val name: String,
    val explanation: String,
    val starGroupName: String
)
