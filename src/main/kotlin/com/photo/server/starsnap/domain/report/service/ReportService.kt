package com.photo.server.starsnap.domain.report.service

import com.photo.server.starsnap.domain.report.SnapReportEntity
import com.photo.server.starsnap.domain.report.UserReportEntity
import com.photo.server.starsnap.domain.report.dto.*
import com.photo.server.starsnap.domain.report.repository.SnapReportRepository
import com.photo.server.starsnap.domain.report.repository.UserReportRepository
import com.photo.server.starsnap.domain.snap.SnapEntity
import com.photo.server.starsnap.domain.user.UserEntity
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
            reporter = reporter,
            snap = snapData
        )

        snapReportRepository.save(snapReport)
    }

    fun userReport(reporter: UserEntity, userData: UserEntity, userReportCreateDto: UserReportCreateDto) {
        val userReport = UserReportEntity(
            id = NanoId.generate(16),
            createdAt = LocalDateTime.now().toString(),
            explanation = userReportCreateDto.explanation,
            reporter = reporter,
            user = userData
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