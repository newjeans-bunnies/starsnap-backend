package com.photo.server.starsnap.domain.report.controller

import com.photo.server.starsnap.domain.report.service.GetReportService
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.global.config.BucketConfig
import com.photo.server.starsnap.global.dto.CommentReportDto
import com.photo.server.starsnap.global.dto.SnapReportDto
import com.photo.server.starsnap.global.dto.UserReportDto
import com.photo.server.starsnap.global.error.exception.TooManyRequestException
import org.springframework.data.domain.Slice
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/report")
class GetReportController(
    private val getReportService: GetReportService,
    private val bucketConfig: BucketConfig
) {
    // snap 신고 내역 가져오기
    @GetMapping("/snaps")
    fun getSnapReport(
        @AuthenticationPrincipalUserData userData: UserEntity,
        @RequestParam size: Int,
        @RequestParam page: Int
    ): Slice<SnapReportDto> {
        if (!bucketConfig.getReportBucket().tryConsume(1)) throw TooManyRequestException
        return getReportService.snapReport(page, size, userData)
    }

    // snap 신고 내역 가져오기
    @GetMapping("/users")
    fun getUserReports(
        @AuthenticationPrincipalUserData userData: UserEntity,
        @RequestParam size: Int,
        @RequestParam page: Int
    ): Slice<UserReportDto> {
        if (!bucketConfig.getReportBucket().tryConsume(1)) throw TooManyRequestException
        return getReportService.userReport(page, size, userData)
    }


    @GetMapping("/comments")
    fun getCommentReports(
        @AuthenticationPrincipalUserData userData: UserEntity,
        @RequestParam size: Int,
        @RequestParam page: Int
    ): Slice<CommentReportDto> {
        if (!bucketConfig.getReportBucket().tryConsume(1)) throw TooManyRequestException
        return getReportService.commentReport(page, size, userData)
    }

}