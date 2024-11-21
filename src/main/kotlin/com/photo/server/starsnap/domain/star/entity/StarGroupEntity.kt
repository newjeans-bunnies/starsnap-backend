package com.photo.server.starsnap.domain.star.entity

import com.photo.server.starsnap.domain.star.entity.base.BaseStarGroupEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "star_group")
class StarGroupEntity(
    name: String,
    debutDate: LocalDateTime,
    explanation: String? = null,
): BaseStarGroupEntity() {
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)")
    var name: String = name

    @Column(name = "explanation", nullable = true, columnDefinition = "VARCHAR(500)")
    val explanation: String? = explanation

    @Column(name = "debut_date", nullable = false, columnDefinition = "DATETIME")
    var debutDate: LocalDateTime = debutDate
}