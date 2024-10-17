package com.photo.server.starsnap.domain.auth.service

import com.photo.server.starsnap.domain.auth.repository.PassKeysRepository
import org.springframework.stereotype.Service

@Service
class PassKeysService(
    private val passKeysRepository: PassKeysRepository
) {
    fun createPassKeys() {

    }

}