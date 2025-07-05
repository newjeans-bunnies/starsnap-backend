package com.photo.server.starsnap.usecase.report.usecase

import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.global.dto.StatusDto

interface DeleteReportUseCase {
    fun deleteUserReport(userReportId: String, user: User): StatusDto

    fun deleteSnapReport(snapReportId: String, user: User): StatusDto

    fun deleteCommentReport(snapCommentId: String, user: User): StatusDto
}