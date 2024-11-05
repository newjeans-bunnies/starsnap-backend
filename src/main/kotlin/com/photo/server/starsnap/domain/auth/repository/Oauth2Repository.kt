package com.photo.server.starsnap.domain.auth.repository

import com.photo.server.starsnap.domain.auth.type.Oauth2
import com.photo.server.starsnap.domain.user.entity.Oauth2Entity
import org.springframework.data.repository.CrudRepository

interface Oauth2Repository: CrudRepository<Oauth2Entity, String> {
    fun findByTypeAndEmail(type: Oauth2, email: String): Oauth2Entity?
    fun existsByTypeAndEmail(type: Oauth2, email: String): Boolean
}