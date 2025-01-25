package com.photo.server.starsnap.domain.report.service

import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.report.dto.toCommentReportDto
import com.photo.server.starsnap.domain.report.dto.toSnapReportDto
import com.photo.server.starsnap.domain.report.dto.toUserReportDto
import com.photo.server.starsnap.domain.report.error.exception.NotFoundCommentReportException
import com.photo.server.starsnap.domain.report.error.exception.NotFoundSnapReportException
import com.photo.server.starsnap.domain.report.error.exception.NotFoundUserReportException
import com.photo.server.starsnap.domain.report.repository.CommentReportRepository
import com.photo.server.starsnap.domain.report.repository.SnapReportRepository
import com.photo.server.starsnap.domain.report.repository.UserReportRepository
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.dto.CommentReportDto
import com.photo.server.starsnap.global.dto.SnapReportDto
import com.photo.server.starsnap.global.dto.UserReportDto
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class GetReportService(
    private val snapReportRepository: SnapReportRepository,
    private val userReportRepository: UserReportRepository,
    private val commentReportRepository: CommentReportRepository
) {
    fun snapReport(page: Int, size: Int, userData: UserEntity): Slice<SnapReportDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val snapReportData = if (userData.authority == Authority.ADMIN) {
            snapReportRepository.findSliceBy(pageRequest) ?: throw NotFoundSnapReportException
        } else {
            snapReportRepository.findSliceBy(pageRequest, userData) ?: throw NotFoundSnapReportException
        }

        return snapReportData.toSnapReportDto()
    }

    fun userReport(page: Int, size: Int, userData: UserEntity): Slice<UserReportDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val userReportData = if (userData.authority == Authority.ADMIN) {
            userReportRepository.findSliceBy(pageRequest) ?: throw NotFoundUserReportException
        } else {
            userReportRepository.findSliceBy(pageRequest, userData) ?: throw NotFoundUserReportException
        }

        return userReportData.toUserReportDto()
    }

    fun commentReport(page: Int, size: Int, userData: UserEntity): Slice<CommentReportDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val commentReportData = if (userData.authority == Authority.ADMIN) {
            commentReportRepository.findSliceBy(pageRequest) ?: throw NotFoundCommentReportException
        } else {
            commentReportRepository.findSliceBy(pageRequest, userData) ?: throw NotFoundCommentReportException
        }


        return commentReportData.toCommentReportDto()
    }
}