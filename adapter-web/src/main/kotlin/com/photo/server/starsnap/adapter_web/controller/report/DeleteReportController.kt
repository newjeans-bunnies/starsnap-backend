package com.photo.server.starsnap.adapter_web.controller.report

import com.photo.server.starsnap.adapter_usecase.report.usecase.DeleteReportUseCaseImpl
import com.photo.server.starsnap.adapter_web.annotation.AuthenticationPrincipalUserData
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/report")
@RestController
class DeleteReportController(
    private val deleteReportUseCaseImpl: DeleteReportUseCaseImpl
) {
    // user 신고 삭제
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/user")
    fun deleteUserReport(
        @RequestParam("user-report-id") userReportId: String,
        @AuthenticationPrincipalUserData user: User
    ): StatusDto {
        return deleteReportUseCaseImpl.deleteUserReport(userReportId, user)
    }

    // snap 신고 삭제
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/snap")
    fun deleteSnapReport(
        @RequestParam("snap-report-id") snapReportId: String,
        @AuthenticationPrincipalUserData user: User
    ): StatusDto {
        return deleteReportUseCaseImpl.deleteSnapReport(snapReportId, user)
    }


    // comment 신고 삭제
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/comment")
    fun deleteCommentReport(
        @RequestParam("comment-report-id") commentReportId: String,
        @AuthenticationPrincipalUserData user: User
    ): StatusDto {
        return deleteReportUseCaseImpl.deleteCommentReport(commentReportId, user)
    }
}