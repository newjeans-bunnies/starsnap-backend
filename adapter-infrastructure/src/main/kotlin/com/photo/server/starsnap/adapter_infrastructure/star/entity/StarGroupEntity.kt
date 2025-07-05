package com.photo.server.starsnap.adapter_infrastructure.star.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import com.photo.server.starsnap.adapter_infrastructure.star.entity.base.StarGroupBaseEntity
import com.photo.server.starsnap.domain.star.entity.StarGroup
import com.photo.server.starsnap.domain.star.type.StarGroupType
import java.time.LocalDateTime

@Entity
@Table(name = "star_group")
class StarGroupEntity(
    @Column(name = "name", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    val name: String,
    @Column(name = "debut_date", nullable = false, columnDefinition = "DATETIME")
    val debutDate: LocalDateTime,
    @Column(name = "star_group_type", nullable = false)
    val starGroupType: StarGroupType,
    @Column(name = "explanation", nullable = true, columnDefinition = "VARCHAR(500)")
    @ColumnDefault("''")
    val explanation: String? = null,
    @Column(name = "image_key", nullable = false, unique = true, columnDefinition = "VARCHAR(500)")
    val imageKey: String? = null,
    @OneToMany(mappedBy = "starGroup", cascade = [CascadeType.REMOVE], fetch = FetchType.EAGER)
    var stars: MutableList<StarEntity> = mutableListOf()
) : StarGroupBaseEntity() {
    fun toDomain() = StarGroup(
        name = this.name,
        debutDate = this.debutDate,
        starGroupType = this.starGroupType,
        explanation = this.explanation,
        imageKey = this.imageKey,
        id = this.id,
    )

    companion object {
        fun fromDomain(starGroup: StarGroup) = StarGroupEntity(
            name = starGroup.name,
            debutDate = starGroup.debutDate,
            starGroupType = starGroup.starGroupType,
            explanation = starGroup.explanation,
            imageKey = starGroup.imageKey
        )
    }
}