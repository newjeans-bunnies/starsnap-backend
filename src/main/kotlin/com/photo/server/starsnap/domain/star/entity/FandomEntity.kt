package com.photo.server.starsnap.domain.star.entity

import com.photo.server.starsnap.domain.star.entity.base.BaseFandomEntity
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "fandom")
class FandomEntity(
    explanation: String = "",
    starGroupEntity: StarGroupEntity
) : BaseFandomEntity() {
    @ColumnDefault("0")
    @Column(name = "total_members", nullable = false, columnDefinition = "UNSIGNED INT")
    val totalMembers: Int = 0

    @ColumnDefault("")
    @Column(name = "explanation", nullable = false, columnDefinition = "VARCHAR(255)")
    val explanation: String = explanation

    @OneToOne(cascade = [(CascadeType.REMOVE)], fetch = FetchType.EAGER)
    @JoinColumn(name = "star_group_id", nullable = false, columnDefinition = "CHAR(16)")
    val starGroupId: StarGroupEntity = starGroupEntity

    @OneToMany(mappedBy = "fandomId", cascade = [(CascadeType.REMOVE)], fetch = FetchType.LAZY)
    val fandoms: List<FandomEntity> = mutableListOf()
}