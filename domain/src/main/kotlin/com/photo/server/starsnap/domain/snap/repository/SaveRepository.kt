package com.photo.server.starsnap.domain.snap.repository

import com.photo.server.starsnap.domain.snap.entity.Save

interface SaveRepository {
    fun deleteByUserIdAndSnapId(userId: String, snapId: String)
    fun save(save: Save): Save
}