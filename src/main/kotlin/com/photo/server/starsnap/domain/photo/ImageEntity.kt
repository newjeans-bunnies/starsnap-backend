package com.photo.server.starsnap.domain.photo

import com.photo.server.starsnap.domain.photo.type.TYPE
import jakarta.persistence.*

@Entity
@Table(name = "image")
data class ImageEntity(
    @Id
    val id: String,
    @Column(name = "url", nullable = false, unique = true, updatable = false)
    val url: String,
    @Column(name = "size", nullable = false)
    var size: Long,
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    var type: TYPE,
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: String,
    @Column(name = "created_by", nullable = false, updatable = false)
    val createdBy: String,
    @Column(name ="source", nullable = false)
    var source: String
)
