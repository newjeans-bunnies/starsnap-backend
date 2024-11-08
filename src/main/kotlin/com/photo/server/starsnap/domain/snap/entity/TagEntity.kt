package com.photo.server.starsnap.domain.snap.entity

import com.photo.server.starsnap.domain.snap.entity.base.TagBaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "tag")
class TagEntity(
    count: Int = 0,
    name: String
) : TagBaseEntity() {
    @Column(name = "count", nullable = false)
    var count: Int = count

    @Column(name = "name", nullable = false, unique = true)
    var name: String = name
        protected set

    @ManyToMany(mappedBy = "tags", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val snaps: List<SnapEntity> = mutableListOf()

    @PostRemove
    fun removeTagEntity() {
        count -= 1
    }

    @PrePersist
    fun createdTagEntity() {
        count += 1
    }
}