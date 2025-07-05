package com.photo.server.starsnap.adapter_infrastructure.snap.repository.springdata

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.CommentEntity
import com.photo.server.starsnap.domain.snap.entity.Snap

@Repository
interface CommentCrudRepository: CrudRepository<CommentEntity, String> {
    @Query("SELECT comment FROM CommentEntity comment where comment.state = true")
    fun findSliceBy(pageable: Pageable): Slice<CommentEntity>?

    @Query("SELECT comment FROM CommentEntity comment WHERE comment.snap = :snapId AND comment.state = true")
    fun findBySnapId(snap: Snap, pageable: Pageable): Slice<CommentEntity>?
}