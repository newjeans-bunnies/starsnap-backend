package com.photo.server.starsnap.domain.photo

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.Column

@Entity
@Table(name = "image")
data class ImageEntity(
    @Id
    val id: String,
    @Column(name = "url", nullable = false, unique = true, updatable = false)
    val url: String,
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: String,
    @Column(name = "created_by", nullable = false, updatable = false)
    val createdBy: String
)
