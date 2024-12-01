package com.photo.server.starsnap.domain.star.entity

import com.photo.server.starsnap.domain.star.entity.base.BaseStarEntity
import com.photo.server.starsnap.domain.star.type.GenderType
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDateTime

@Entity
@Table(name = "star")
class StarEntity(
    name: String,
    gender: GenderType,
    birthday: LocalDateTime,
    explanation: String? = null,
    starGroup: StarGroupEntity? = null,
    nickname: String,
    imageKey: String? = null,
) : BaseStarEntity() {
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)")
    var name: String = name // 이름

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender", nullable = false)
    var gender: GenderType = gender // 성별

    @Column(name = "birthday", nullable = true, columnDefinition = "DATETIME")
    var birthday: LocalDateTime = birthday // 생일

    @Column(name = "explanation", nullable = false, columnDefinition = "VARCHAR(500)")
    @ColumnDefault("''")
    var explanation: String? = explanation // 설명

    @Column(name = "nickname", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    var nickname: String = nickname // 닉네임

    @Column(name = "image_key", nullable = false, unique = true, columnDefinition = "VARCHAR(500)")
    val imageKey: String? = imageKey


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "star_group_id", nullable = true, columnDefinition = "CHAR(16)")
    var starGroupId: StarGroupEntity? = starGroup
}