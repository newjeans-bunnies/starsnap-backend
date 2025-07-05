package com.photo.server.starsnap.adapter_infrastructure.report.repository.springdata

import com.photo.server.starsnap.adapter_infrastructure.report.entity.SnapReportEntity
import jakarta.persistence.LockModeType
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity

@Repository
interface SnapReportCrudRepository : CrudRepository<SnapReportEntity, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT snapReport FROM SnapReportEntity snapReport")
    fun findSliceBy(pageable: Pageable): Slice<SnapReportEntity>?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT snapReport FROM SnapReportEntity snapReport WHERE snapReport.reporter = :userEntity")
    fun findSliceBy(pageable: Pageable, userEntity: UserEntity): Slice<SnapReportEntity>?

}