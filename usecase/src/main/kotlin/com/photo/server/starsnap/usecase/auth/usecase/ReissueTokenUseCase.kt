package com.photo.server.starsnap.usecase.auth.usecase

import com.photo.server.starsnap.domain.auth.entity.RefreshToken
import com.photo.server.starsnap.usecase.auth.dto.TokenDto
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws

interface ReissueTokenUseCase {
    fun reissueToken(refreshToken: String, accessToken: String): TokenDto

    fun checkValidRefreshToken(refreshToken: String): RefreshToken

    fun getClaims(token: String): Boolean

    fun checkValidAccessToken(accessToken: String, refreshTokenClaims: Jws<Claims>)
}