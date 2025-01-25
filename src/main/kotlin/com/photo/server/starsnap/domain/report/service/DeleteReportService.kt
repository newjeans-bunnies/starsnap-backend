package com.photo.server.starsnap.domain.report.service

import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.report.error.exception.NotFoundSnapReportException
import com.photo.server.starsnap.domain.report.error.exception.NotFoundUserReportException
import com.photo.server.starsnap.domain.report.repository.CommentReportRepository
import com.photo.server.starsnap.domain.report.repository.SnapReportRepository
import com.photo.server.starsnap.domain.report.repository.UserReportRepository
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.error.exception.InvalidRoleException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DeleteReportService(
    private val snapReportRepository: SnapReportRepository,
    private val userReportRepository: UserReportRepository,
    private val commentReportRepository: CommentReportRepository
) {
    fun deleteUserReport(userReportId: String, user: UserEntity): StatusDto {
        val userReport = userReportRepository.findByIdOrNull(userReportId) ?: throw NotFoundUserReportException
        if (userReport.reporter != user || user.authority != Authority.ADMIN) throw InvalidRoleException
        userReportRepository.delete(userReport)

        return StatusDto("정상적으로 삭제됨", 204)
    }

    fun deleteSnapReport(snapReportId: String, user: UserEntity): StatusDto {
        val snapReport = snapReportRepository.findByIdOrNull(snapReportId) ?: throw NotFoundSnapReportException
        if (snapReport.reporter != user || user.authority != Authority.ADMIN) throw InvalidRoleException
        snapReportRepository.delete(snapReport)

        return StatusDto("정상적으로 삭제됨", 204)
    }

    fun deleteCommentReport(snapCommentId: String, user: UserEntity): StatusDto {
        val snapReport = commentReportRepository.findByIdOrNull(snapCommentId) ?: throw NotFoundSnapReportException
        if (snapReport.reporter != user || user.authority != Authority.ADMIN) throw InvalidRoleException
        commentReportRepository.delete(snapReport)

        return StatusDto("정상적으로 삭제됨", 204)
    }
}