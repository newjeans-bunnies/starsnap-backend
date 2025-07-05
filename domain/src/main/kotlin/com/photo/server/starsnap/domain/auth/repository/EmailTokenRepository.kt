package com.photo.server.starsnap.domain.auth.repository

import com.photo.server.starsnap.domain.auth.entity.EmailToken

interface EmailTokenRepository {
    fun save(emailToken: EmailToken): EmailToken
    fun delete(emailToken: EmailToken)
    fun findByIdOrNull(email: String): EmailToken?
}