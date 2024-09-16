package com.photo.server.starsnap.domain.report.controller

import com.photo.server.starsnap.domain.report.dto.SnapReportCreateDto
import com.photo.server.starsnap.domain.report.dto.SnapReportDto
import com.photo.server.starsnap.domain.report.dto.UserReportCreateDto
import com.photo.server.starsnap.domain.report.dto.UserReportDto
import com.photo.server.starsnap.domain.report.service.ReportService
import com.photo.server.starsnap.domain.snap.repository.SnapRepository
import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import org.springframework.data.domain.Slice
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/report")
class ReportController(
    private val reportService: ReportService,
    private val snapRepository: SnapRepository,
    private val userRepository: UserRepository
) {
    @PostMapping("/snap")
    fun snapReport(
        @AuthenticationPrincipal user: CustomUserDetails,
        @RequestBody snapReportCreateDto: SnapReportCreateDto
    ) {
        val snapEntity =
            snapRepository.findById(snapReportCreateDto.snapId).orElseThrow { RuntimeException("존재 하지 않는 snap") }
        reportService.snapReport(user.getUserData(), snapEntity, snapReportCreateDto)
    }

    @PostMapping("/user")
    fun userReport(
        @AuthenticationPrincipal user: CustomUserDetails,
        @RequestBody userReportCreateDto: UserReportCreateDto
    ) {
        val userEntity =
            userRepository.findById(userReportCreateDto.userId).orElseThrow { RuntimeException("존재 하지 않는 user") }
        reportService.userReport(user.getUserData(), userEntity, userReportCreateDto)
    }


    @GetMapping("/snap")
    fun getSnapReport(
        @RequestParam size: Int,
        @RequestParam page: Int
    ): Slice<SnapReportDto> {
        return reportService.getSnapReport(page, size)
    }


    @GetMapping("/user")
    fun getUserReports(
        @RequestParam size: Int,
        @RequestParam page: Int
    ): Slice<UserReportDto> {
        return reportService.getUserReport(page, size)
    }


}