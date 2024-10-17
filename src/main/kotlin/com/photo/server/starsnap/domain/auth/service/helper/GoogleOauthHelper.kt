package com.photo.server.starsnap.domain.auth.service.helper

import com.photo.server.starsnap.domain.auth.client.GoogleClient
import com.photo.server.starsnap.domain.auth.dto.OIDCDecodePayload
import com.photo.server.starsnap.global.security.jwt.JwtProperties
import org.springframework.stereotype.Component

@Component
class GoogleOauthHelper(
    private val googleClient: GoogleClient,
    private val jwtProperties: JwtProperties,
    private val oauthOIDCHelper: OauthOIDCHelper
) {
    fun getOIDCDecodePayload(token: String): OIDCDecodePayload {
        val oidcPublicKey = googleClient.getGoogleOIDCOpenKeys()
        return oauthOIDCHelper.getPayloadFromIdToken(
            token,
            jwtProperties.googleAppId,
            jwtProperties.googleBaseUrl,
            oidcPublicKey
        )
    }
}