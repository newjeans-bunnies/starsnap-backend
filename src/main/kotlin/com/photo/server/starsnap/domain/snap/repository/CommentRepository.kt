package com.photo.server.starsnap.domain.snap.repository

import com.photo.server.starsnap.domain.snap.entity.CommentEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CommentRepository: CrudRepository<CommentEntity, String> {
    @Query("SELECT comment FROM CommentEntity comment where comment.state = true")
    fun findSliceBy(pageable: Pageable): Slice<CommentEntity>?
}