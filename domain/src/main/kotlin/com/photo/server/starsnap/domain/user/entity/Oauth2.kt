package com.photo.server.starsnap.domain.user.entity

import com.photo.server.starsnap.domain.auth.type.Oauth2IssuerUris
import com.photo.server.starsnap.domain.auth.type.Oauth2Type
import com.photo.server.starsnap.domain.user.entity.base.Oauth2Base
import java.time.LocalDateTime

data class Oauth2(
    val type: Oauth2Type,
    val email: String,
    val sub: String,
    val user: User,
    val issuerUris: Oauth2IssuerUris,
    override val id: String,
    override val createdAt: LocalDateTime?,
) : Oauth2Base(id, createdAt)