package com.photo.server.starsnap.domain.report.repository

import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.report.entity.CommentReport
import com.photo.server.starsnap.domain.user.entity.User

interface CommentReportRepository {
    fun findSliceBy(pageable: PageRequest): Slice<CommentReport>?

    fun findSliceBy(pageable: PageRequest, userEntity: User): Slice<CommentReport>?

    fun findByIdOrNull(id: String): CommentReport?

    fun delete(commentReport: CommentReport)

    fun save(commentReport: CommentReport): CommentReport
}