package com.photo.server.starsnap.domain.auth.repository

import com.photo.server.starsnap.domain.auth.entity.EmailVerify

interface EmailVerifyRepository {
    fun findByIdOrNull(id: String): EmailVerify?
    fun delete(emailVerify: EmailVerify)
    fun save(emailVerify: EmailVerify): EmailVerify
}