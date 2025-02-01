package com.photo.server.starsnap.domain.snap.repository

import com.photo.server.starsnap.domain.snap.entity.SnapEntity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface SnapRepository : JpaRepository<SnapEntity, String> {
    // 모든 snap를 가져오는 SQL 문(admin이 모든 snap을 가져올때 사용하는 SQL문)
    @Query("SELECT snap FROM SnapEntity snap")
    fun findSliceBy(pageable: Pageable): Slice<SnapEntity>?

    // 상태가 false이고 시간이 지난 snap을 삭제(유저가 삭제하고 30일 뒤에 자동으로 삭제하는 SQL문)
    @Modifying
    @Query("DELETE FROM SnapEntity s WHERE s.state = false AND s.modifiedAt <= :sevenDaysAgo")
    fun deleteOldSnaps(sevenDaysAgo: LocalDateTime)


    @Query("""
    SELECT snap 
    FROM SnapEntity snap
    WHERE (snap.state = :state) 
      AND (:blockUser IS NULL OR snap.user NOT IN :blockUser)
      AND (:tags IS NULL OR snap.tags IN :tags)
      AND (:title IS NULL OR snap.title LIKE %:title%)
      AND (:user IS NULL OR snap.user IN :user)
""")
    fun findFilteredSnaps(
        pageable: Pageable,
        state: Boolean,
        blockUser: List<UserEntity>?,
        tags: List<String>?,
        title: String?,
        user: UserEntity?
    ): Slice<SnapEntity>
}