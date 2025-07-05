package com.photo.server.starsnap.adapter_usecase.report.usecase

import com.photo.server.starsnap.adapter_infrastructure.report.repository.CommentReportRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.report.repository.SnapReportRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.report.repository.UserReportRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.CommentRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.SnapRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.user.repository.UserRepositoryImpl
import com.photo.server.starsnap.domain.report.entity.CommentReport
import com.photo.server.starsnap.domain.report.entity.SnapReport
import com.photo.server.starsnap.domain.report.entity.UserReport
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.exception.star.error.exception.NotFoundStarIdException
import com.photo.server.starsnap.exception.user.error.exception.NotFoundUserIdException
import com.photo.server.starsnap.usecase.report.dto.CommentReportCreateDto
import com.photo.server.starsnap.usecase.report.dto.SnapReportCreateDto
import com.photo.server.starsnap.usecase.report.dto.UserReportCreateDto
import com.photo.server.starsnap.usecase.report.usecase.ReportUseCase
import org.springframework.stereotype.Service
import io.viascom.nanoid.NanoId
import java.time.LocalDateTime

@Service
class ReportUseCaseImpl(
    private val snapReportRepositoryImpl: SnapReportRepositoryImpl,
    private val userReportRepositoryImpl: UserReportRepositoryImpl,
    private val commentReportRepositoryImpl: CommentReportRepositoryImpl,
    private val commentRepositoryImpl: CommentRepositoryImpl,
    private val userRepositoryImpl: UserRepositoryImpl,
    private val snapRepositoryImpl: SnapRepositoryImpl,
): ReportUseCase {
    // snap 신고
     override fun snapReport(reporter: User, snapReportCreateDto: SnapReportCreateDto) {
        val snapData = snapRepositoryImpl.findByIdOrNull(snapReportCreateDto.snapId) ?: throw NotFoundStarIdException
        val snapReport = SnapReport(
            explanation = snapReportCreateDto.explanation,
            snap = snapData,
            reporter = reporter,
            id = NanoId.generate(16),
            createdAt = LocalDateTime.now()
        )

        snapReportRepositoryImpl.save(snapReport)
    }

    // user 신고
    override fun userReport(reporter: User, userReportCreateDto: UserReportCreateDto) {
        val defendant = userRepositoryImpl.findByIdOrNull(userReportCreateDto.userId) ?: throw NotFoundUserIdException

        val userReport = UserReport(
            explanation = userReportCreateDto.explanation,
            reporter = reporter,
            defendant = defendant,
            id = NanoId.generate(16),
            createdAt = LocalDateTime.now()
        )

        userReportRepositoryImpl.save(userReport)
    }

    // comment 신고
    override fun commentReport(reporter: User, commentReportCreateDto: CommentReportCreateDto) {
        val commentData = commentRepositoryImpl.findByIdOrNull(commentReportCreateDto.commentId) ?: throw NotFoundUserIdException

        val commentReport = CommentReport(
            explanation = commentReportCreateDto.explanation,
            reporter = reporter,
            comment = commentData,
            id = NanoId.generate(16),
            createdAt = LocalDateTime.now()
        )

        commentReportRepositoryImpl.save(commentReport)
    }
}