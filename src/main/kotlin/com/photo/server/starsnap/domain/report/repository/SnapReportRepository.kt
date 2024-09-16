package com.photo.server.starsnap.domain.report.repository

import com.photo.server.starsnap.domain.report.entity.SnapReportEntity
import jakarta.persistence.LockModeType
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface SnapReportRepository : CrudRepository<SnapReportEntity, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT snapReport FROM SnapReportEntity snapReport")
    fun findSliceBy(pageable: Pageable): Slice<SnapReportEntity>?
}