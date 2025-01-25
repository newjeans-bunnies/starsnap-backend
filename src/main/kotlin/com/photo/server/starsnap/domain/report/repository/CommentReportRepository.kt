package com.photo.server.starsnap.domain.report.repository

import com.photo.server.starsnap.domain.report.entity.CommentReportEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import jakarta.persistence.LockModeType
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CommentReportRepository : CrudRepository<CommentReportEntity, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT commentReport FROM CommentReportEntity commentReport")
    fun findSliceBy(pageable: Pageable): Slice<CommentReportEntity>?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT commentReport FROM CommentReportEntity commentReport WHERE commentReport.reporter = :userEntity")
    fun findSliceBy(pageable: Pageable, userEntity: UserEntity): Slice<CommentReportEntity>?

}