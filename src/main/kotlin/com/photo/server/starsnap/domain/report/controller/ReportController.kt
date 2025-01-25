package com.photo.server.starsnap.domain.report.controller

import com.photo.server.starsnap.domain.report.dto.SnapReportCreateDto
import com.photo.server.starsnap.domain.report.dto.UserReportCreateDto
import com.photo.server.starsnap.domain.report.dto.CommentReportCreateDto
import com.photo.server.starsnap.domain.report.service.ReportService
import com.photo.server.starsnap.domain.snap.repository.CommentRepository
import com.photo.server.starsnap.domain.snap.repository.SnapRepository
import com.photo.server.starsnap.domain.star.error.exception.NotFoundStarIdException
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.domain.user.error.exception.NotFoundUserIdException
import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.global.config.BucketConfig
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.error.exception.TooManyRequestException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/report")
class ReportController(
    private val reportService: ReportService,
    private val snapRepository: SnapRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
    private val bucketConfig: BucketConfig
) {

    // snap 신고
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/snap")
    fun snapReport(
        @AuthenticationPrincipalUserData userData: UserEntity, @RequestBody snapReportCreateDto: SnapReportCreateDto
    ): StatusDto {
        if (!bucketConfig.reportBucket().tryConsume(1)) throw TooManyRequestException

        val snapEntity = snapRepository.findByIdOrNull(snapReportCreateDto.snapId) ?: throw NotFoundStarIdException
        reportService.snapReport(userData, snapEntity, snapReportCreateDto)
        return StatusDto("created snap report", 201)
    }

    // user 신고
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user")
    fun userReport(
        @AuthenticationPrincipalUserData userData: UserEntity, @RequestBody userReportCreateDto: UserReportCreateDto
    ): StatusDto {
        if (!bucketConfig.reportBucket().tryConsume(1)) throw TooManyRequestException

        val userEntity = userRepository.findByIdOrNull(userReportCreateDto.snapId) ?: throw NotFoundUserIdException
        reportService.userReport(userData, userEntity, userReportCreateDto)
        return StatusDto("created user report", 201)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/comment")
    fun commentReport(
        @AuthenticationPrincipalUserData userData: UserEntity, @RequestBody commentReportCreateDto: CommentReportCreateDto
    ): StatusDto {
        if (!bucketConfig.reportBucket().tryConsume(1)) throw TooManyRequestException

        val userEntity = commentRepository.findByIdOrNull(commentReportCreateDto.commentId) ?: throw NotFoundUserIdException
        reportService.commentReport(userData, userEntity, commentReportCreateDto)
        return StatusDto("created comment report", 201)
    }

}