package com.photo.server.starsnap.domain.star.entity

import com.photo.server.starsnap.domain.star.entity.base.BaseStarEntity
import com.photo.server.starsnap.domain.star.type.GenderType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "star")
class StarEntity(
    name: String,
    gender: GenderType,
    birthday: LocalDateTime? = null,
    explanation: String? = null,
    starGroup: StarGroupEntity? = null,
) : BaseStarEntity() {
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)")
    val name: String = name

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender", nullable = false)
    val gender: GenderType = gender

    @Column(name = "birthday", nullable = true, columnDefinition = "DATETIME")
    val birthday: LocalDateTime? = birthday

    @Column(name = "explanation", nullable = true, columnDefinition = "VARCHAR(500)")
    val explanation: String? = explanation


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "star_group_id", nullable = true, columnDefinition = "CHAR(16)")
    var starGroupId: StarGroupEntity? = starGroup
}