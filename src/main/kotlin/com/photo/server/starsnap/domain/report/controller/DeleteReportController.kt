package com.photo.server.starsnap.domain.report.controller

import com.photo.server.starsnap.domain.report.service.DeleteReportService
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.global.dto.StatusDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/report")
@RestController
class DeleteReportController(
    private val deleteReportService: DeleteReportService
) {
    // user 신고 삭제
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/user")
    fun deleteUserReport(
        @RequestParam("report-id") reportId: String,
        @AuthenticationPrincipalUserData user: UserEntity
    ): StatusDto {
        return deleteReportService.deleteUserReport(reportId, user)
    }

    // snap 신고 삭제
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/snap")
    fun deleteSnapReport(
        @RequestParam("report-id") reportId: String,
        @AuthenticationPrincipalUserData user: UserEntity
    ): StatusDto {
        return deleteReportService.deleteSnapReport(reportId, user)
    }


    // comment 신고 삭제
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/comment")
    fun deleteCommentReport(
        @RequestParam("report-id") reportId: String,
        @AuthenticationPrincipalUserData user: UserEntity
    ): StatusDto {
        return deleteReportService.deleteCommentReport(reportId, user)
    }
}