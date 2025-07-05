package com.photo.server.starsnap.adapter_web.controller.report

import com.photo.server.starsnap.adapter_infrastructure.snap.repository.CommentRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.SnapRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.user.repository.UserRepositoryImpl
import com.photo.server.starsnap.adapter_usecase.report.usecase.ReportUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.config.BucketConfig
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.exception.global.error.exception.TooManyRequestException
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.report.dto.CommentReportCreateDto
import com.photo.server.starsnap.usecase.report.dto.SnapReportCreateDto
import com.photo.server.starsnap.usecase.report.dto.UserReportCreateDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/report")
class ReportController(
    private val reportUseCaseImpl: ReportUseCaseImpl,
    private val snapRepositoryImpl: SnapRepositoryImpl,
    private val userRepositoryImpl: UserRepositoryImpl,
    private val commentRepositoryImpl: CommentRepositoryImpl,
    private val bucketConfig: BucketConfig
) {

    // snap 신고
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/snap")
    fun snapReport(
        @AuthenticationPrincipalUserData userData: User,
        @RequestBody snapReportCreateDto: SnapReportCreateDto
    ): StatusDto {
        if (!bucketConfig.reportBucket().tryConsume(1)) throw TooManyRequestException

        reportUseCaseImpl.snapReport(userData, snapReportCreateDto)
        return StatusDto("created snap report", 201)
    }

    // user 신고
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user")
    fun userReport(
        @AuthenticationPrincipalUserData userData: User, @RequestBody userReportCreateDto: UserReportCreateDto
    ): StatusDto {
        if (!bucketConfig.reportBucket().tryConsume(1)) throw TooManyRequestException

        reportUseCaseImpl.userReport(userData, userReportCreateDto)
        return StatusDto("created user report", 201)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/comment")
    fun commentReport(
        @AuthenticationPrincipalUserData userData: User,
        @RequestBody commentReportCreateDto: CommentReportCreateDto
    ): StatusDto {
        if (!bucketConfig.reportBucket().tryConsume(1)) throw TooManyRequestException

        reportUseCaseImpl.commentReport(userData, commentReportCreateDto)
        return StatusDto("created comment report", 201)
    }

}