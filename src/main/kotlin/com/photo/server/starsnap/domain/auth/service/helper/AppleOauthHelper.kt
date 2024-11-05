package com.photo.server.starsnap.domain.auth.service.helper

import com.photo.server.starsnap.domain.auth.client.AppleClient
import com.photo.server.starsnap.domain.auth.dto.OIDCDecodePayload
import com.photo.server.starsnap.global.security.jwt.JwtProperties
import org.springframework.stereotype.Component

@Component
class AppleOauthHelper(
    private val appleClient: AppleClient,
    private val oauthOIDCHelper: OauthOIDCHelper,
    private val jwtProperties: JwtProperties
) {
    fun getOIDCDecodePayload(token: String): OIDCDecodePayload {
        val oidcPublicKey = appleClient.getAppleOIDCOpenKeys()
        return oauthOIDCHelper.getPayloadFromIdToken(
            token,
            jwtProperties.googleAppId,
            jwtProperties.googleBaseUrl,
            oidcPublicKey
        )
    }
}