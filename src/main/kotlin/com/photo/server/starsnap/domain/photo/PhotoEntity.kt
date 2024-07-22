package com.photo.server.starsnap.domain.photo

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Column

@Entity
@Table(name = "photo")
data class PhotoEntity(
    @Id
    val id: String,
    @Column(unique = true, nullable = false, updatable = false)
    val url: String,
    @Column(nullable = false)
    val name: String
)
