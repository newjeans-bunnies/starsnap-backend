package com.photo.server.starsnap.adapter_infrastructure.snap.repository

import org.springframework.stereotype.Repository
import com.photo.server.starsnap.adapter_infrastructure.extension.toCommonSlice
import com.photo.server.starsnap.adapter_infrastructure.extension.toSpringPageable
import com.photo.server.starsnap.adapter_infrastructure.snap.SnapMapper.toSnap
import com.photo.server.starsnap.adapter_infrastructure.snap.entity.SnapEntity
import com.photo.server.starsnap.adapter_infrastructure.user.entity.UserEntity
import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.common.map
import com.photo.server.starsnap.domain.snap.entity.Snap
import com.photo.server.starsnap.domain.snap.repository.SnapRepository
import com.photo.server.starsnap.domain.user.entity.User
import org.springframework.data.repository.findByIdOrNull
import com.photo.server.starsnap.adapter_infrastructure.snap.repository.springdata.SnapCrudRepository
import java.time.LocalDateTime

@Repository
class SnapRepositoryImpl(
    private val snapCrudRepository: SnapCrudRepository
) : SnapRepository {
    override fun findSliceBy(pageable: PageRequest): Slice<Snap>? {
        return snapCrudRepository.findSliceBy(pageable.toSpringPageable())?.toCommonSlice()?.map { it.toSnap() }
    }

    override fun deleteOldSnaps(sevenDaysAgo: LocalDateTime) {
        return snapCrudRepository.deleteOldSnaps(sevenDaysAgo)
    }

    override fun findFilteredSnaps(
        pageable: PageRequest,
        state: Boolean,
        blockUser: List<User>?,
        tags: List<String>,
        title: String,
        userId: String,
        star: List<String>,
        starGroup: List<String>
    ): Slice<Snap>? {
        return snapCrudRepository.findFilteredSnaps(
            pageable.toSpringPageable(),
            state,
            blockUser?.map { UserEntity.fromDomain(it) },
            tags,
            title,
            userId,
            star,
            starGroup
        )?.toCommonSlice()?.map { it.toSnap() }
    }

    override fun findByIdOrNull(snapId: String?) = snapCrudRepository.findByIdOrNull(snapId)?.toSnap()

    override fun existsById(snapId: String) = snapCrudRepository.existsById(snapId)
    override fun save(snap: Snap): Snap {
        val snapEntity = SnapEntity.fromDomain(snap)
        return snapCrudRepository.save(snapEntity).toSnap()
    }

    override fun findAll(): List<Snap>? {
        return snapCrudRepository.findAll().map { it.toSnap() }
    }


}