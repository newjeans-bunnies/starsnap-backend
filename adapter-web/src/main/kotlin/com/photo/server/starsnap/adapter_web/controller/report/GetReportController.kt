package com.photo.server.starsnap.adapter_web.controller.report

import com.photo.server.starsnap.adapter_usecase.report.usecase.GetReportUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.config.BucketConfig
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.exception.global.error.exception.TooManyRequestException
import com.photo.server.starsnap.usecase.report.dto.CommentReportDto
import com.photo.server.starsnap.usecase.report.dto.SnapReportDto
import com.photo.server.starsnap.usecase.report.dto.UserReportDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/report")
class GetReportController(
    private val getReportUseCaseImpl: GetReportUseCaseImpl,
    private val bucketConfig: BucketConfig
) {
    // snap 신고 내역 가져오기
    @GetMapping("/snap")
    fun getSnapReport(
        @AuthenticationPrincipalUserData userData: User,
        @RequestParam size: Int,
        @RequestParam page: Int
    ): Slice<SnapReportDto> {
        if (!bucketConfig.getReportBucket().tryConsume(1)) throw TooManyRequestException
        return getReportUseCaseImpl.snapReport(page, size, userData)
    }

    // snap 신고 내역 가져오기
    @GetMapping("/user")
    fun getUserReports(
        @AuthenticationPrincipalUserData userData: User,
        @RequestParam size: Int,
        @RequestParam page: Int
    ): Slice<UserReportDto> {
        if (!bucketConfig.getReportBucket().tryConsume(1)) throw TooManyRequestException
        return getReportUseCaseImpl.userReport(page, size, userData)
    }


    @GetMapping("/comment")
    fun getCommentReports(
        @AuthenticationPrincipalUserData userData: User,
        @RequestParam size: Int,
        @RequestParam page: Int
    ): Slice<CommentReportDto> {
        if (!bucketConfig.getReportBucket().tryConsume(1)) throw TooManyRequestException
        return getReportUseCaseImpl.commentReport(page, size, userData)
    }

}