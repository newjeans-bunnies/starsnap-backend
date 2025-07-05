package com.photo.server.starsnap.adapter_infrastructure.snap.entity

import com.photo.server.starsnap.adapter_infrastructure.snap.entity.base.BaseTagEntity
import com.photo.server.starsnap.domain.snap.entity.Tag
import jakarta.persistence.*

@Entity
@Table(name = "tag")
class TagEntity(
    @Column(name = "count", nullable = false, columnDefinition = "INT UNSIGNED")
    var count: Int = 0,
    @Column(name = "name", nullable = false, unique = true, columnDefinition = "VARCHAR(10)")
    val name: String,
    @ManyToMany(mappedBy = "tags", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    val snaps: List<SnapEntity> = mutableListOf()
) : BaseTagEntity() {

    fun toDomain() = Tag(
        name = this.name,
        count = this.count,
        id = this.id,
    )

    companion object {
        fun fromDomain(tag: Tag) = TagEntity(
            count = tag.count,
            name = tag.name
        )
    }

    @PostRemove
    fun removeTagEntity() {
        count -= 1
    }

    @PrePersist
    fun createdTagEntity() {
        count += 1
    }
}