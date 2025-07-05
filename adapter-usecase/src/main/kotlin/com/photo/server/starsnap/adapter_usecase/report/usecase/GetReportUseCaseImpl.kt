package com.photo.server.starsnap.adapter_usecase.report.usecase

import com.photo.server.starsnap.adapter_infrastructure.report.repository.CommentReportRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.report.repository.SnapReportRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.report.repository.UserReportRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.extension.toDomainPageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.domain.user.type.Authority
import com.photo.server.starsnap.exception.report.error.exception.NotFoundCommentReportException
import com.photo.server.starsnap.exception.report.error.exception.NotFoundSnapReportException
import com.photo.server.starsnap.exception.report.error.exception.NotFoundUserReportException
import com.photo.server.starsnap.usecase.report.dto.CommentReportDto
import com.photo.server.starsnap.usecase.report.dto.SnapReportDto
import com.photo.server.starsnap.usecase.report.dto.UserReportDto
import com.photo.server.starsnap.usecase.report.dto.toCommentReportDto
import com.photo.server.starsnap.usecase.report.dto.toSnapReportDto
import com.photo.server.starsnap.usecase.report.dto.toUserReportDto
import com.photo.server.starsnap.usecase.report.usecase.GetReportUseCase
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class GetReportUseCaseImpl(
    private val snapReportRepositoryImpl: SnapReportRepositoryImpl,
    private val userReportRepositoryImpl: UserReportRepositoryImpl,
    private val commentReportRepositoryImpl: CommentReportRepositoryImpl
) : GetReportUseCase {
    // snap 신고 조회
    override fun snapReport(page: Int, size: Int, userData: User): Slice<SnapReportDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val snapReportData = if (userData.authority == Authority.ADMIN) {
            snapReportRepositoryImpl.findSliceBy(pageRequest.toDomainPageRequest()) ?: throw NotFoundSnapReportException
        } else {
            snapReportRepositoryImpl.findSliceBy(pageRequest.toDomainPageRequest(), userData)
                ?: throw NotFoundSnapReportException
        }

        return snapReportData.toSnapReportDto()
    }

    // user 신고 조회
    override fun userReport(page: Int, size: Int, userData: User): Slice<UserReportDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val userReportData = if (userData.authority == Authority.ADMIN) {
            userReportRepositoryImpl.findSliceBy(pageRequest.toDomainPageRequest()) ?: throw NotFoundUserReportException
        } else {
            userReportRepositoryImpl.findSliceBy(pageRequest.toDomainPageRequest(), userData)
                ?: throw NotFoundUserReportException
        }

        return userReportData.toUserReportDto()
    }

    // comment 신고 조회
    override fun commentReport(page: Int, size: Int, userData: User): Slice<CommentReportDto> {
        val pageRequest = PageRequest.of(
            page, size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val commentReportData = if (userData.authority == Authority.ADMIN) {
            commentReportRepositoryImpl.findSliceBy(pageRequest.toDomainPageRequest())
                ?: throw NotFoundCommentReportException
        } else {
            commentReportRepositoryImpl.findSliceBy(pageRequest.toDomainPageRequest(), userData)
                ?: throw NotFoundCommentReportException
        }


        return commentReportData.toCommentReportDto()
    }
}