package com.photo.server.starsnap.domain.report.repository

import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.report.entity.UserReport
import com.photo.server.starsnap.domain.user.entity.User

interface UserReportRepository {
    fun findSliceBy(pageable: PageRequest): Slice<UserReport>?

    fun findSliceBy(pageable: PageRequest, userEntity: User): Slice<UserReport>?

    fun findByIdOrNull(id: String): UserReport?

    fun delete(userReport: UserReport)

    fun save(userReport: UserReport): UserReport
}