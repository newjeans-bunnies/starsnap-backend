package com.photo.server.starsnap.usecase.report.usecase

import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.report.dto.CommentReportDto
import com.photo.server.starsnap.usecase.report.dto.SnapReportDto
import com.photo.server.starsnap.usecase.report.dto.UserReportDto

interface GetReportUseCase {
    fun snapReport(page: Int, size: Int, userData: User): Slice<SnapReportDto>

    fun userReport(page: Int, size: Int, userData: User): Slice<UserReportDto>

    fun commentReport(page: Int, size: Int, userData: User): Slice<CommentReportDto>
}
