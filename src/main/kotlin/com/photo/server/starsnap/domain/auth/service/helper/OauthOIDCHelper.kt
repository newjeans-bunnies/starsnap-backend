package com.photo.server.starsnap.domain.auth.service.helper

import com.photo.server.starsnap.domain.auth.dto.OIDCDecodePayload
import com.photo.server.starsnap.domain.auth.dto.OIDCOpenKeys
import com.photo.server.starsnap.global.security.jwt.JwtOIDCProvider
import org.springframework.stereotype.Component

@Component
class OauthOIDCHelper(
    private val jwtOIDCProvider: JwtOIDCProvider
) {
    private fun getKidFromUnsignedInToken(token: String, iss: String, aud: String): String {
        return jwtOIDCProvider.getKidFromUnsignedTokenHeader(token, iss, aud)
    }

    fun getPayloadFromIdToken(token: String, aud: String, iss: String, oidcOpenKeys: OIDCOpenKeys): OIDCDecodePayload {
        val kid = getKidFromUnsignedInToken(token, iss, aud)
        val oidcPublicKeyDto = oidcOpenKeys.keys.stream()
            .filter { o ->
                o.kid == kid
            }
            .findFirst()
            .orElseThrow()

        return jwtOIDCProvider.getOIDCTokenBody(
            token, oidcPublicKeyDto.n, oidcPublicKeyDto.e
        )
    }
}