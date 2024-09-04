package com.photo.server.starsnap.domain.report.repository

import com.photo.server.starsnap.domain.report.SnapReportEntity
import com.photo.server.starsnap.domain.snap.SnapEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface SnapReportRepository : CrudRepository<SnapReportEntity, String> {
    @Query("SELECT snapReport FROM SnapReportEntity snapReport")
    fun findSliceBy(pageable: Pageable): Slice<SnapReportEntity>?
}