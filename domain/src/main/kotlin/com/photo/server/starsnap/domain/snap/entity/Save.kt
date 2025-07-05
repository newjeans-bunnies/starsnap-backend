package com.photo.server.starsnap.domain.snap.entity

import com.photo.server.starsnap.domain.snap.entity.base.BaseSave
import com.photo.server.starsnap.domain.user.entity.User
import java.time.LocalDateTime

data class Save(
    val user: User,
    val snap: Snap,
    override val id: String,
    override val saveTime: LocalDateTime?
) : BaseSave(id, saveTime)