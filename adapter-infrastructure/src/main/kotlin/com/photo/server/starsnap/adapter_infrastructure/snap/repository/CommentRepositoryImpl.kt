package com.photo.server.starsnap.adapter_infrastructure.snap.repository

import com.photo.server.starsnap.adapter_infrastructure.extension.toCommonSlice
import com.photo.server.starsnap.adapter_infrastructure.extension.toSpringPageable
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.CommentEntity
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.springdata.CommentCrudRepository
import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import com.photo.server.starsnap.domain.snap.entity.Comment
import com.photo.server.starsnap.domain.snap.entity.Snap
import com.photo.server.starsnap.domain.snap.repository.CommentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CommentRepositoryImpl(
    private val commentCrudRepository: CommentCrudRepository
): CommentRepository {
    override fun findSliceBy(pageable: PageRequest): Slice<Comment>? {
        return commentCrudRepository.findSliceBy(pageable.toSpringPageable())?.toCommonSlice()?.map { it.toDomain() }
    }

    override fun findByIdOrNull(id: String): Comment? {
        return commentCrudRepository.findByIdOrNull(id)?.toDomain()
    }

    override fun save(comment: Comment): Comment {
        val commentEntity = CommentEntity.fromDomain(comment)
        return commentCrudRepository.save(commentEntity).toDomain()
    }

    override fun delete(comment: Comment) {
        val commentEntity = CommentEntity.fromDomain(comment)
        commentCrudRepository.delete(commentEntity)
    }

    override fun findBySnapId(snap: Snap, pageable: PageRequest): Slice<Comment>? {
        return commentCrudRepository.findBySnapId(snap, pageable.toSpringPageable())
            ?.toCommonSlice()
            ?.map { it.toDomain() }
    }
}