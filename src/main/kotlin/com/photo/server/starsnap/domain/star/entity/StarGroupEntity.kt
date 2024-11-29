package com.photo.server.starsnap.domain.star.entity

import com.photo.server.starsnap.domain.star.entity.base.BaseStarGroupEntity
import com.photo.server.starsnap.domain.star.type.StarGroupType
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDateTime

@Entity
@Table(name = "star_group")
class StarGroupEntity(
    name: String,
    debutDate: LocalDateTime,
    starGroupType: StarGroupType,
    explanation: String? = null,
) : BaseStarGroupEntity() {
    @Column(name = "name", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    var name: String = name

    @Column(name = "explanation", nullable = true, columnDefinition = "VARCHAR(500)")
    @ColumnDefault("''")
    var explanation: String? = explanation

    @Column(name = "debut_date", nullable = false, columnDefinition = "DATETIME")
    var debutDate: LocalDateTime = debutDate

    @Column(name = "star_group_type", nullable = false)
    var starGroupType: StarGroupType = starGroupType

    @OneToMany(mappedBy = "starGroupId", cascade = [CascadeType.REMOVE], fetch = FetchType.EAGER)
    var stars: MutableList<StarEntity> = mutableListOf()
}