package com.photo.server.starsnap.domain.snap.repository

import com.photo.server.starsnap.domain.snap.SnapEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SnapRepository : JpaRepository<SnapEntity, String> {
    @Query("SELECT snap FROM SnapEntity snap")
    fun findSliceBy(pageable: Pageable): Slice<SnapEntity>?
}