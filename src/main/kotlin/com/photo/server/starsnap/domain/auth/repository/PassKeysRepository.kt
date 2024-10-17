package com.photo.server.starsnap.domain.auth.repository

import com.photo.server.starsnap.domain.auth.entity.PassKeysEntity
import org.springframework.data.repository.CrudRepository

interface PassKeysRepository: CrudRepository<PassKeysEntity, String> {
}