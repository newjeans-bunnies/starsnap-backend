package com.photo.server.starsnap.domain.auth.repository

import com.photo.server.starsnap.domain.auth.entity.RefreshToken

interface RefreshTokenRepository {
    fun save(token: RefreshToken): RefreshToken
    fun findByToken(token: String): RefreshToken?
    fun delete(token: RefreshToken)
    fun findByIdOrNull(id: String): RefreshToken?
}