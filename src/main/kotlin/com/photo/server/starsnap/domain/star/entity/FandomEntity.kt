package com.photo.server.starsnap.domain.star.entity

import com.photo.server.starsnap.domain.star.entity.base.BaseFandomEntity
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "fandom")
class FandomEntity(
    name: String,
    explanation: String = "",
    starGroupEntity: StarGroupEntity
) : BaseFandomEntity() {
    @Column(name = "total_members", nullable = false, columnDefinition = "INT UNSIGNED")
    val totalMembers: Int = 0

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)")
    var name: String = name

    @Column(name = "explanation", nullable = false, columnDefinition = "VARCHAR(255)")
    var explanation: String = explanation

    @OneToOne(cascade = [(CascadeType.REMOVE)], fetch = FetchType.EAGER)
    @JoinColumn(name = "star_group_id", nullable = false, columnDefinition = "CHAR(16)")
    val starGroupId: StarGroupEntity = starGroupEntity

    @OneToMany(mappedBy = "fandom", cascade = [(CascadeType.REMOVE)], fetch = FetchType.LAZY)
    val fandomJoins: List<FandomJoinEntity> = mutableListOf()
}