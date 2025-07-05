package com.photo.server.starsnap.domain.snap.repository

import com.photo.server.starsnap.domain.common.PageRequest
import com.photo.server.starsnap.domain.common.Slice
import com.photo.server.starsnap.domain.snap.entity.Snap
import com.photo.server.starsnap.domain.user.entity.User
import java.time.LocalDateTime

interface SnapRepository {
    fun findSliceBy(pageable: PageRequest): Slice<Snap>?

    fun deleteOldSnaps(sevenDaysAgo: LocalDateTime)

    fun findFilteredSnaps(
        pageable: PageRequest,
        state: Boolean,
        blockUser: List<User>? = null,
        tags: List<String> = listOf(),
        title: String = "",
        userId: String = "",
        star: List<String> = listOf(),
        starGroup: List<String> = listOf(),
    ): Slice<Snap>?

    fun findByIdOrNull(snapId: String?): Snap?

    fun existsById(snapId: String): Boolean

    fun save(snap: Snap): Snap

    fun findAll(): List<Snap>?
}