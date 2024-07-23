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
    @Column(nullable = false)
    val name: String,
    @Column(name = "created_by", nullable = false, updatable = false)
    val createBy: String,
    @Column(name = "create_at", nullable = false)
    val createAt: String
)