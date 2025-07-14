package com.photo.server.starsnap.adapter_infrastructure.star.entity

import jakarta.persistence.*
import com.photo.server.starsnap.adapter_infrastructure.star.entity.base.FandomBaseEntity
import com.photo.server.starsnap.domain.star.entity.Fandom

@Entity
@Table(name = "fandom")
class FandomEntity(
    @Column(name = "total_members", nullable = false, columnDefinition = "INT UNSIGNED")
    var totalMembers: Int,
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)")
    val name: String,
    @Column(name = "explanation", nullable = false, columnDefinition = "VARCHAR(255)")
    val explanation: String = "",
    @OneToOne(cascade = [(CascadeType.REMOVE)], fetch = FetchType.EAGER)
    @JoinColumn(name = "star_group_id", nullable = false, columnDefinition = "CHAR(16)")
    val starGroup: StarGroupEntity
) : FandomBaseEntity() {

    @OneToMany(mappedBy = "fandom", cascade = [(CascadeType.REMOVE)], fetch = FetchType.LAZY)
    val fandomJoins: List<FandomJoinEntity> = mutableListOf()


    companion object {
        fun fromDomain(fandom: Fandom) = FandomEntity(
            totalMembers = fandom.totalMembers,
            name = fandom.name,
            explanation = fandom.explanation,
            starGroup = StarGroupEntity.fromDomain(fandom.starGroup)
        )
    }
}