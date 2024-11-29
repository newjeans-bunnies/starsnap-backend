package com.photo.server.starsnap.domain.snap.entity

import com.photo.server.starsnap.domain.snap.entity.base.BaseTagEntity
import jakarta.persistence.*

@Entity
@Table(name = "tag")
class TagEntity(
    count: Int = 0,
    name: String
) : BaseTagEntity() {
    @Column(name = "count", nullable = false, columnDefinition = "INT UNSIGNED")
    var count: Int = count

    @Column(name = "name", nullable = false, unique = true, columnDefinition = "VARCHAR(10)")
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