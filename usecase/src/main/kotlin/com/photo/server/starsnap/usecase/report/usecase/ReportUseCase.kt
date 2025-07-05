package com.photo.server.starsnap.usecase.report.usecase

import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.report.dto.CommentReportCreateDto
import com.photo.server.starsnap.usecase.report.dto.SnapReportCreateDto
import com.photo.server.starsnap.usecase.report.dto.UserReportCreateDto

interface ReportUseCase {
    fun snapReport(reporter: User, snapReportCreateDto: SnapReportCreateDto)

    fun userReport(reporter: User, userReportCreateDto: UserReportCreateDto)

    fun commentReport(reporter: User, commentReportCreateDto: CommentReportCreateDto)
}