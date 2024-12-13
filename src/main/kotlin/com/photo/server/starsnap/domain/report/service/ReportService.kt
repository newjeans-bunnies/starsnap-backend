package com.photo.server.starsnap.domain.report.service

import com.photo.server.starsnap.domain.report.entity.SnapReportEntity
import com.photo.server.starsnap.domain.report.entity.UserReportEntity
import com.photo.server.starsnap.domain.report.dto.*
import com.photo.server.starsnap.domain.report.error.exception.NotFoundSnapReportException
import com.photo.server.starsnap.domain.report.error.exception.NotFoundUserReportException
import com.photo.server.starsnap.domain.report.repository.SnapReportRepository
import com.photo.server.starsnap.domain.report.repository.UserReportRepository
import com.photo.server.starsnap.domain.snap.entity.SnapEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.dto.SnapReportDto
import com.photo.server.starsnap.global.dto.UserReportDto
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
            snap = snapData,
            reporter = reporter
        )

        snapReportRepository.save(snapReport)
    }

    fun userReport(reporter: UserEntity, defendant: UserEntity, userReportCreateDto: UserReportCreateDto) {
        val userReport = UserReportEntity(
            id = NanoId.generate(16),
            createdAt = LocalDateTime.now().toString(),
            explanation = userReportCreateDto.explanation,
            reporter = reporter,
            defendant = defendant
        )

        userReportRepository.save(userReport)
    }

    fun getSnapReport(page: Int, size: Int): Slice<SnapReportDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val snapReportData = snapReportRepository.findSliceBy(pageRequest) ?: throw NotFoundSnapReportException

        return snapReportData.toSnapReportDto()
    }

    fun getUserReport(page: Int, size: Int): Slice<UserReportDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val userReportData = userReportRepository.findSliceBy(pageRequest) ?: throw NotFoundUserReportException

        return userReportData.toUserReportDto()
    }

}