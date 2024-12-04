package com.photo.server.starsnap.domain.star.dto

data class CreateFandomRequestDto(val name: String, val explanation: String)
data class UpdateFandomRequestDto(val name: String, val explanation: String)
data class FandomDto(val name: String, val explanation: String)