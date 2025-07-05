package com.photo.server.starsnap.adapter_usecase.report.usecase

import com.photo.server.starsnap.adapter_infrastructure.report.repository.CommentReportRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.report.repository.SnapReportRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.report.repository.UserReportRepositoryImpl
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.domain.user.type.Authority
import com.photo.server.starsnap.exception.global.error.exception.InvalidRoleException
import com.photo.server.starsnap.exception.report.error.exception.NotFoundSnapReportException
import com.photo.server.starsnap.exception.report.error.exception.NotFoundUserReportException
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.report.usecase.DeleteReportUseCase
import org.springframework.stereotype.Service

@Service
class DeleteReportUseCaseImpl(
    private val snapReportRepositoryImpl: SnapReportRepositoryImpl,
    private val userReportRepositoryImpl: UserReportRepositoryImpl,
    private val commentReportRepositoryImpl: CommentReportRepositoryImpl
) : DeleteReportUseCase {
    // 유저 신고 삭제
    override fun deleteUserReport(userReportId: String, user: User): StatusDto {
        val userReport = userReportRepositoryImpl.findByIdOrNull(userReportId) ?: throw NotFoundUserReportException
        if (userReport.reporter != user || user.authority != Authority.ADMIN) throw InvalidRoleException
        userReportRepositoryImpl.delete(userReport)

        return StatusDto("정상적으로 삭제됨", 204)
    }

    // 스냅 신고 삭제
    override fun deleteSnapReport(snapReportId: String, user: User): StatusDto {
        val snapReport = snapReportRepositoryImpl.findByIdOrNull(snapReportId) ?: throw NotFoundSnapReportException
        if (snapReport.reporter != user || user.authority != Authority.ADMIN) throw InvalidRoleException
        snapReportRepositoryImpl.delete(snapReport)

        return StatusDto("정상적으로 삭제됨", 204)
    }

    // 댓글 신고 삭제
    override fun deleteCommentReport(snapCommentId: String, user: User): StatusDto {
        val snapReport = commentReportRepositoryImpl.findByIdOrNull(snapCommentId) ?: throw NotFoundSnapReportException
        if (snapReport.reporter != user || user.authority != Authority.ADMIN) throw InvalidRoleException
        commentReportRepositoryImpl.delete(snapReport)

        return StatusDto("정상적으로 삭제됨", 204)
    }
}