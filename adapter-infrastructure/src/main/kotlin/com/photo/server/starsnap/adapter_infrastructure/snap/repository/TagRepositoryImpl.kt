package com.photo.server.starsnap.adapter_infrastructure.snap.repository

import com.photo.server.starsnap.adapter_infrastructure.snap.entity.TagEntity
import com.photo.server.starsnap.domain.snap.entity.Tag
import com.photo.server.starsnap.domain.snap.repository.TagRepository
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.springdata.TagCrudRepository

@Repository
class TagRepositoryImpl(
    private val tagCrudRepository: TagCrudRepository
): TagRepository {
    override fun existsByName(name: String): Boolean {
        return tagCrudRepository.existsByName(name)
    }

    override fun findByName(name: String): Tag? {
        return tagCrudRepository.findByName(name)?.toDomain()
    }

    override fun save(tag: Tag): Tag {
        val tagEntity = TagEntity.fromDomain(tag)
        return tagCrudRepository.save(tagEntity).toDomain()
    }
}