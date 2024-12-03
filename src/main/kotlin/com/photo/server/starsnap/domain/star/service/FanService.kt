package com.photo.server.starsnap.domain.star.service

import com.photo.server.starsnap.domain.star.repository.FanRepository
import org.springframework.stereotype.Service

@Service
class FanService(
    private val fanRepository: FanRepository
) {
    fun joinFan() {}
    fun disconnectFan() {}
}