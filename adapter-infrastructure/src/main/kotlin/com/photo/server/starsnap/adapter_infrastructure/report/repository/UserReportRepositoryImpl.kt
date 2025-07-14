package com.photo.server.starsnap.adapter_infrastructure.report.repository

import com.photo.server.starsnap.adapter_infrastructure.report.entity.UserReportEntity
import com.photo.server.starsnap.adapter_infrastructure.extension.toCommonSlice
import com.photo.server.starsnap.adapter_infrastructure.extension.toSpringPageable
import com.photo.server.starsnap.adapter_infrastructure.report.ReportMapper.toUserReport
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.adapter_infrastructure.report.repository.springdata.UserReportCrudRepository
import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import com.photo.server.starsnap.domain.report.entity.UserReport
import com.photo.server.starsnap.domain.report.repository.UserReportRepository
import com.photo.server.starsnap.domain.user.entity.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserReportRepositoryImpl(
    private val userReportCrudRepository: UserReportCrudRepository
): UserReportRepository {
    override fun findSliceBy(pageable: PageRequest): Slice<UserReport>? {
        return userReportCrudRepository.findSliceBy(pageable.toSpringPageable())?.toCommonSlice()?.map { it.toUserReport() }
    }

    override fun findSliceBy(pageable: PageRequest, userEntity: User): Slice<UserReport>? {
        return userReportCrudRepository.findSliceBy(pageable.toSpringPageable(), UserEntity.fromDomain(userEntity))?.toCommonSlice()?.map { it.toUserReport() }
    }

    override fun findByIdOrNull(id: String): UserReport? {
        return userReportCrudRepository.findByIdOrNull(id)?.toUserReport()
    }

    override fun delete(userReport: UserReport) {
        userReportCrudRepository.delete(UserReportEntity.fromDomain(userReport))
    }

    override fun save(userReport: UserReport): UserReport {
        return userReportCrudRepository.save(UserReportEntity.fromDomain(userReport)).toUserReport()
    }
}