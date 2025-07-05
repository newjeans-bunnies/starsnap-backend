package com.photo.server.starsnap.domain.snap.repository

import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.snap.entity.Comment
import com.photo.server.starsnap.domain.snap.entity.Snap

interface CommentRepository {
    fun findSliceBy(pageable: PageRequest): Slice<Comment>?
    fun findByIdOrNull(id: String): Comment?
    fun findBySnapId(snap: Snap, pageable: PageRequest): Slice<Comment>?
    fun save(comment: Comment): Comment
    fun delete(comment: Comment)
}