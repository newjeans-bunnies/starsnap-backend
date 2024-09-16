package com.photo.server.starsnap.domain.auth.repository

import com.photo.server.starsnap.domain.auth.entity.EmailTokenEntity
import org.springframework.data.repository.CrudRepository

interface EmailTokenRepository : CrudRepository<EmailTokenEntity, String>