package com.photo.server.starsnap.domain.star

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id

@Entity
@Table(name = "star")
data class StarEntity(
    @Id
    val id: String,
    @Column(name = "name", nullable = false, unique = true)
    val name: String,
    @Column(name = "introduction", nullable = false)
    val introduction: String,
    @Column(name = "created_date", nullable = false, columnDefinition = "DATA")
    val createdDate: String,
    @Column(name = "representative_photo_url", unique = true, nullable = false)
    val representativePhotoUrl: String,
    @Column(name = "representative_logo_url", nullable = false, unique = true)
    val representativeLogoUrl: String
)
