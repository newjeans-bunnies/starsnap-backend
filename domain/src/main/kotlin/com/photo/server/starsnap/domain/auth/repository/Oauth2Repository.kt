package com.photo.server.starsnap.domain.auth.repository

import com.photo.server.starsnap.domain.auth.type.Oauth2Type
import com.photo.server.starsnap.domain.user.entity.Oauth2

interface Oauth2Repository {
    fun findByTypeAndEmail(type: Oauth2Type, email: String): Oauth2?
    fun existsByTypeAndEmail(type: Oauth2Type, email: String): Boolean
    fun delete(oauth2: Oauth2)
    fun save(oauth2: Oauth2): Oauth2
}