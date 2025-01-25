package com.photo.server.starsnap.domain.report.service

import com.photo.server.starsnap.domain.report.entity.SnapReportEntity
import com.photo.server.starsnap.domain.report.entity.UserReportEntity
import com.photo.server.starsnap.domain.report.dto.CommentReportCreateDto
import com.photo.server.starsnap.domain.report.dto.SnapReportCreateDto
import com.photo.server.starsnap.domain.report.dto.UserReportCreateDto
import com.photo.server.starsnap.domain.report.entity.CommentReportEntity
import com.photo.server.starsnap.domain.report.repository.CommentReportRepository
import com.photo.server.starsnap.domain.report.repository.SnapReportRepository
import com.photo.server.starsnap.domain.report.repository.UserReportRepository
import com.photo.server.starsnap.domain.snap.entity.CommentEntity
import com.photo.server.starsnap.domain.snap.entity.SnapEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import org.springframework.stereotype.Service

@Service
class ReportService(
    private val snapReportRepository: SnapReportRepository,
    private val userReportRepository: UserReportRepository,
    private val commentReportRepository: CommentReportRepository
) {
    // snap 신고
    fun snapReport(reporter: UserEntity, snapData: SnapEntity, snapReportCreateDto: SnapReportCreateDto) {
        val snapReport = SnapReportEntity(
            explanation = snapReportCreateDto.explanation,
            snap = snapData,
            reporter = reporter
        )

        snapReportRepository.save(snapReport)
    }

    // user 신고
    fun userReport(reporter: UserEntity, defendant: UserEntity, userReportCreateDto: UserReportCreateDto) {
        val userReport = UserReportEntity(
            explanation = userReportCreateDto.explanation,
            reporter = reporter,
            defendant = defendant
        )

        userReportRepository.save(userReport)
    }

    // comment 신고
    fun commentReport(reporter: UserEntity, commentData: CommentEntity ,commentReportCreateDto: CommentReportCreateDto) {
        val commentReport = CommentReportEntity(
            explanation = commentReportCreateDto.explanation,
            reporter = reporter,
            comment = commentData
        )

        commentReportRepository.save(commentReport)
    }
}