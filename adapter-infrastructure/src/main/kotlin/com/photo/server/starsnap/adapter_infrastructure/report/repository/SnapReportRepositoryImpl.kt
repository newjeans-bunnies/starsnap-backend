package com.photo.server.starsnap.adapter_infrastructure.report.repository

import com.photo.server.starsnap.adapter_infrastructure.report.entity.SnapReportEntity
import com.photo.server.starsnap.adapter_infrastructure.report.repository.springdata.SnapReportCrudRepository
import com.photo.server.starsnap.adapter_infrastructure.extension.toCommonSlice
import com.photo.server.starsnap.adapter_infrastructure.extension.toSpringPageable
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import com.photo.server.starsnap.domain.report.entity.SnapReport
import com.photo.server.starsnap.domain.report.repository.SnapReportRepository
import com.photo.server.starsnap.domain.user.entity.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class SnapReportRepositoryImpl(
    private val snapReportCrudRepository: SnapReportCrudRepository
): SnapReportRepository {
    override fun findSliceBy(pageable: PageRequest): Slice<SnapReport>? {
        return snapReportCrudRepository.findSliceBy(pageable.toSpringPageable())?.toCommonSlice()?.map { it.toDomain() }
    }

    override fun findSliceBy(pageable: PageRequest, userEntity: User): Slice<SnapReport>? {
        return snapReportCrudRepository.findSliceBy(pageable.toSpringPageable(), UserEntity.fromDomain(userEntity))?.toCommonSlice()?.map { it.toDomain() }
    }

    override fun findByIdOrNull(id: String): SnapReport? {
        return snapReportCrudRepository.findByIdOrNull(id)?.toDomain()
    }

    override fun delete(snapReport: SnapReport) {
        snapReportCrudRepository.delete(SnapReportEntity.fromDomain(snapReport))
    }

    override fun save(snapReport: SnapReport): SnapReport {
        return snapReportCrudRepository.save(SnapReportEntity.fromDomain(snapReport)).toDomain()
    }
}