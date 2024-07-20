package com.photo.server.starsnap.domain.auth.repository

import com.photo.server.starsnap.domain.auth.EmailVerifyEntity
import org.springframework.data.repository.CrudRepository

interface EmailVerifyRepository: CrudRepository<EmailVerifyEntity, String>