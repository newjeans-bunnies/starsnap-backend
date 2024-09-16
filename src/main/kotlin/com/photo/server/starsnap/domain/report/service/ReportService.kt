package com.photo.server.starsnap.domain.report.service

import com.photo.server.starsnap.domain.report.entity.SnapReportEntity
import com.photo.server.starsnap.domain.report.entity.UserReportEntity
import com.photo.server.starsnap.domain.report.dto.*
import com.photo.server.starsnap.domain.report.repository.SnapReportRepository
import com.photo.server.starsnap.domain.report.repository.UserReportRepository
import com.photo.server.starsnap.domain.snap.SnapEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import io.viascom.nanoid.NanoId
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReportService(
    private val snapReportRepository: SnapReportRepository,
    private val userReportRepository: UserReportRepository
) {
    fun snapReport(reporter: UserEntity, snapData: SnapEntity, snapReportCreateDto: SnapReportCreateDto) {
        val snapReport = SnapReportEntity(
            id = NanoId.generate(16),
            createdAt = LocalDateTime.now().toString(),
            explanation = snapReportCreateDto.explanation,
            reporterUsername = reporter.username,
            reporterId = reporter.id,
            reporterAuthority = reporter.authority.name,
            reporterEmail = reporter.email,
            snapId = snapData.id,
            snapSize = snapData.size,
            snapType = snapData.type,
            snapTitle = snapData.title,
            snapSource = snapData.source,
            snapCreatedAt = snapData.createdAt,
            snapDateTaken = snapData.dateTaken,
            snapImageKey = snapData.imageKey,
            snapImageWidth = snapData.imageWidth,
            snapImageHeight = snapData.imageHeight
        )

        snapReportRepository.save(snapReport)
    }

    fun userReport(reporter: UserEntity, defendant: UserEntity, userReportCreateDto: UserReportCreateDto) {
        val userReport = UserReportEntity(
            id = NanoId.generate(16),
            createdAt = LocalDateTime.now().toString(),
            explanation = userReportCreateDto.explanation,
            reporterId = reporter.id,
            reporterUsername = reporter.username,
            reporterAuthority = reporter.authority.name,
            reporterEmail = reporter.email,
            defendantId = defendant.id,
            defendantUsername = defendant.username,
            defendantEmail = defendant.email,
            defendantAuthority = defendant.authority
        )

        userReportRepository.save(userReport)
    }

    fun getSnapReport(page: Int, size: Int): Slice<SnapReportDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val snapReportData = snapReportRepository.findSliceBy(pageRequest) ?: throw RuntimeException("존재 하지 않는 snapReport")

        return snapReportData.toSnapReportDto()
    }

    fun getUserReport(page: Int, size: Int): Slice<UserReportDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val userReportData = userReportRepository.findSliceBy(pageRequest) ?: throw RuntimeException("존재 하지 않는 userReport")

        return userReportData.toUserReportDto()
    }

}