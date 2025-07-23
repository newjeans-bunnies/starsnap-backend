package com.photo.server.starsnap.adapter_infrastructure.report.repository

import com.photo.server.starsnap.adapter_infrastructure.report.entity.CommentReportEntity
import com.photo.server.starsnap.adapter_infrastructure.global.extension.toCommonSlice
import com.photo.server.starsnap.adapter_infrastructure.global.extension.toSpringPageable
import com.photo.server.starsnap.adapter_infrastructure.report.ReportMapper.toCommentReport
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.report.entity.CommentReport
import com.photo.server.starsnap.domain.report.repository.CommentReportRepository
import com.photo.server.starsnap.domain.user.entity.User
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.report.repository.springdata.CommentReportCrudRepository
import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import org.springframework.data.repository.findByIdOrNull

@Repository
class CommentReportRepositoryImpl(
    private val commentReportCrudRepository: CommentReportCrudRepository
): CommentReportRepository {
    override fun findSliceBy(pageable: PageRequest): Slice<CommentReport>? {
        return commentReportCrudRepository.findSliceBy(pageable.toSpringPageable())?.toCommonSlice()?.map { it.toCommentReport() }
    }

    override fun findSliceBy(pageable: PageRequest, userEntity: User): Slice<CommentReport>? {
        return commentReportCrudRepository.findSliceBy(pageable.toSpringPageable(), UserEntity.fromDomain(userEntity))?.toCommonSlice()?.map { it.toCommentReport() }
    }

    override fun findByIdOrNull(id: String): CommentReport? {
        return commentReportCrudRepository.findByIdOrNull(id)?.toCommentReport()
    }

    override fun delete(commentReport: CommentReport) {
        commentReportCrudRepository.delete(CommentReportEntity.fromDomain(commentReport))
    }

    override fun save(commentReport: CommentReport): CommentReport {
        return commentReportCrudRepository.save(CommentReportEntity.fromDomain(commentReport)).toCommentReport()
    }

}