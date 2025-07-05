package com.photo.server.starsnap.domain.report.repository

import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.report.entity.SnapReport
import com.photo.server.starsnap.domain.user.entity.User

interface SnapReportRepository {
    fun findSliceBy(pageable: PageRequest): Slice<SnapReport>?

    fun findSliceBy(pageable: PageRequest, user: User): Slice<SnapReport>?

    fun findByIdOrNull(id: String): SnapReport?

    fun delete(snapReport: SnapReport)

    fun save(snapReport: SnapReport): SnapReport
}