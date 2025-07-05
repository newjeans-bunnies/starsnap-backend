package com.photo.server.starsnap.adapter_infrastructure.report.repository.springdata

import com.photo.server.starsnap.adapter_infrastructure.report.entity.UserReportEntity
import jakarta.persistence.LockModeType
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity

@Repository
interface UserReportCrudRepository: CrudRepository<UserReportEntity, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT userReport FROM UserReportEntity userReport")
    fun findSliceBy(pageable: Pageable): Slice<UserReportEntity>?


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT userReport FROM UserReportEntity userReport WHERE userReport.reporter = :userEntity")
    fun findSliceBy(pageable: Pageable, userEntity: UserEntity): Slice<UserReportEntity>?
}