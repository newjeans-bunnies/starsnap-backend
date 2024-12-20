package com.photo.server.starsnap.domain.report.controller

import com.photo.server.starsnap.domain.report.dto.SnapReportCreateDto
import com.photo.server.starsnap.domain.report.dto.UserReportCreateDto
import com.photo.server.starsnap.domain.report.service.ReportService
import com.photo.server.starsnap.domain.snap.repository.SnapRepository
import com.photo.server.starsnap.domain.star.error.exception.NotFoundStarIdException
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.domain.user.error.exception.NotFoundUserIdException
import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.global.config.BucketConfig
import com.photo.server.starsnap.global.dto.SnapReportDto
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.dto.UserReportDto
import com.photo.server.starsnap.global.error.exception.TooManyRequestException
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/report")
class ReportController(
    private val reportService: ReportService,
    private val snapRepository: SnapRepository,
    private val userRepository: UserRepository,
    private val bucketConfig: BucketConfig
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/snap")
    fun snapReport(
        @AuthenticationPrincipalUserData userData: UserEntity,
        @RequestBody snapReportCreateDto: SnapReportCreateDto
    ): StatusDto {
        if(!bucketConfig.reportBucket().tryConsume(1)) throw TooManyRequestException
        val snapEntity = snapRepository.findByIdOrNull(snapReportCreateDto.snapId) ?: throw NotFoundStarIdException
        reportService.snapReport(userData, snapEntity, snapReportCreateDto)

        return StatusDto("created snap report", 201)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user")
    fun userReport(
        @AuthenticationPrincipalUserData userData: UserEntity,
        @RequestBody userReportCreateDto: UserReportCreateDto
    ): StatusDto {
        if(!bucketConfig.reportBucket().tryConsume(1)) throw TooManyRequestException

        val userEntity =
            userRepository.findByIdOrNull(userReportCreateDto.userId)?: throw NotFoundUserIdException
        reportService.userReport(userData, userEntity, userReportCreateDto)
        return StatusDto("created user report", 201)
    }


    @GetMapping("/snap")
    fun getSnapReport(
        @AuthenticationPrincipalUserData userData: UserEntity,
        @RequestParam size: Int,
        @RequestParam page: Int
    ): Slice<SnapReportDto> {
        if(!bucketConfig.getReportBucket().tryConsume(1)) throw TooManyRequestException
        return reportService.getSnapReport(page, size)
    }


    @GetMapping("/user")
    fun getUserReports(
        @AuthenticationPrincipalUserData userData: UserEntity,
        @RequestParam size: Int,
        @RequestParam page: Int
    ): Slice<UserReportDto> {
        if(!bucketConfig.getReportBucket().tryConsume(1)) throw TooManyRequestException
        return reportService.getUserReport(page, size)
    }


}