package com.photo.server.starsnap.domain.report.repository

import com.photo.server.starsnap.domain.report.UserReportEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface UserReportRepository: CrudRepository<UserReportEntity, String> {
    @Query("SELECT userReport FROM UserReportEntity userReport")
    fun findSliceBy(pageable: Pageable): Slice<UserReportEntity>?
}