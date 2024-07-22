package com.photo.server.starsnap.domain.star.service

import com.photo.server.starsnap.domain.star.repository.StarRepository
import org.springframework.stereotype.Service

@Service
class StarService(
    private val starRepository: StarRepository
) {
}