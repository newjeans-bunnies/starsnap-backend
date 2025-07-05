package com.photo.server.starsnap.adapter_infrastructure.auth.repository.springdata

import com.photo.server.starsnap.adapter_infrastructure.auth.entity.EmailTokenEntity
import org.springframework.data.repository.CrudRepository

interface EmailTokenCrudRepository : CrudRepository<EmailTokenEntity, String>