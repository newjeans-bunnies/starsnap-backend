package com.photo.server.starsnap.adapter_infrastructure.report.repository.springdata

import com.photo.server.starsnap.adapter_infrastructure.report.entity.CommentReportEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import jakarta.persistence.LockModeType
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentReportCrudRepository : CrudRepository<CommentReportEntity, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT commentReport FROM CommentReportEntity commentReport")
    fun findSliceBy(pageable: Pageable): Slice<CommentReportEntity>?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT commentReport FROM CommentReportEntity commentReport WHERE commentReport.reporter = :userEntity")
    fun findSliceBy(pageable: Pageable, userEntity: UserEntity): Slice<CommentReportEntity>?

}