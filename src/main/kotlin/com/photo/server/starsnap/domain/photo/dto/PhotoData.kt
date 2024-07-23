package com.photo.server.starsnap.domain.photo.dto

data class PhotoData(
    val name: String,
    val createdAt: String,
    val createdBy: String,
    val imageUrl: List<String>
)
