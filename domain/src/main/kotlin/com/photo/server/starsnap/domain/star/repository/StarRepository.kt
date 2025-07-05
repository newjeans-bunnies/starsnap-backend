package com.photo.server.starsnap.domain.star.repository

import com.photo.server.starsnap.domain.star.entity.Star

interface StarRepository {
    fun existsByName(name: String): Boolean
    fun existsByNickname(nickname: String): Boolean
    fun findByIdOrNull(starId: String): Star?
    fun save(star: Star): Star
    fun existsById(id: String): Boolean
}