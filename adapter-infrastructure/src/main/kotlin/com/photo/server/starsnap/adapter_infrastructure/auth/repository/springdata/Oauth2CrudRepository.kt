package com.photo.server.starsnap.adapter_infrastructure.auth.repository.springdata

import org.springframework.data.repository.CrudRepository
import com.photo.server.starsnap.adapter_infrastructure.user.entity.Oauth2Entity
import com.photo.server.starsnap.domain.auth.type.Oauth2Type

interface Oauth2CrudRepository: CrudRepository<Oauth2Entity, String> {
    fun findByTypeAndEmail(type: Oauth2Type, email: String): Oauth2Entity?
    fun existsByTypeAndEmail(type: Oauth2Type, email: String): Boolean
}