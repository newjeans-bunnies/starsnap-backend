package com.photo.server.starsnap.domain.star.service

import com.photo.server.starsnap.domain.star.repository.FandomRepository
import org.springframework.stereotype.Service

@Service
class FandomService(
    private val fandomRepository: FandomRepository
) {
    fun createFandom() {}
    fun updateFandom() {}
    fun deleteFandom() {}

    fun joinFandom() {}
    fun disconnectFandom() {}
}