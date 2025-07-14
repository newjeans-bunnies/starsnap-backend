package com.photo.server.starsnap.adapter_infrastructure.star.entity

import jakarta.persistence.*
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SnapEntity
import com.photo.server.starsnap.adapter_infrastructure.star.entity.base.StarBaseEntity
import com.photo.server.starsnap.domain.star.entity.Star
import com.photo.server.starsnap.domain.star.type.GenderType
import java.time.LocalDateTime

@Entity
@Table(name = "star")
class StarEntity(
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)")
    val name: String,
    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender", nullable = false)
    val gender: GenderType,
    @Column(name = "birthday", nullable = true, columnDefinition = "DATETIME")
    val birthday: LocalDateTime,
    @Column(name = "explanation", nullable = false, columnDefinition = "VARCHAR(500)")
    val explanation: String? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "star_group_id", nullable = true, columnDefinition = "CHAR(16)")
    val starGroup: StarGroupEntity? = null,

    @Column(name = "nickname", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    val nickname: String,

    @Column(name = "image_key", nullable = false, unique = true, columnDefinition = "VARCHAR(500)")
    val imageKey: String? = null,
) : StarBaseEntity() {

    @OneToMany(mappedBy = "star", cascade = [CascadeType.REMOVE])
    var fans: MutableList<FanEntity> = mutableListOf()

    @ManyToMany(mappedBy = "stars", cascade = [CascadeType.REMOVE])
    var snaps: MutableList<SnapEntity> = mutableListOf()

    companion object {
        fun fromDomain(star: Star) = StarEntity(
            name = star.name,
            gender = star.gender,
            birthday = star.birthday,
            explanation = star.explanation,
            nickname = star.nickname
        )
    }

}