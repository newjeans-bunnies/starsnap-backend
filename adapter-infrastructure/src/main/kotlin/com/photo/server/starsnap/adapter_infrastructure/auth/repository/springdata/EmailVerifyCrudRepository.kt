package com.photo.server.starsnap.adapter_infrastructure.auth.repository.springdata

import com.photo.server.starsnap.adapter_infrastructure.auth.entity.EmailVerifyEntity
import org.springframework.data.repository.CrudRepository

interface EmailVerifyCrudRepository: CrudRepository<EmailVerifyEntity, String>