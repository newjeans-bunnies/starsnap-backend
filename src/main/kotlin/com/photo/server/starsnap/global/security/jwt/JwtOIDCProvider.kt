package com.photo.server.starsnap.global.security.jwt

import com.photo.server.starsnap.domain.auth.dto.OIDCDecodePayload
import io.jsonwebtoken.*
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.security.KeyFactory
import java.security.Key
import java.security.spec.RSAPublicKeySpec
import java.util.Base64
import com.photo.server.starsnap.domain.auth.type.issToOauth2
import com.photo.server.starsnap.global.error.exception.InvalidTokenException

@Component
class JwtOIDCProvider {

    fun getKidFromUnsignedTokenHeader(token: String, iss: String, aud: String): String {
        return getUnsignedTokenClaims(token, iss, aud).header["kid"].toString()
    }

    fun getOIDCTokenBody(token: String, modulus: String, exponent: String): OIDCDecodePayload {

        val body = getOIDCTokenJws(token, modulus, exponent).body
        println(body)
        return OIDCDecodePayload(
            body.issuer,
            body.audience,
            body.subject,
            body["email"].toString(),
            body.issuer.issToOauth2(),
            body["name"].toString(),
            body["picture"].toString()
        )
    }

    fun getOIDCTokenJws(token: String, modulus: String, exponent: String): Jws<Claims> {
        return JwsParseExecuter {
            Jwts.parserBuilder()
                .setSigningKey(getRSAPublicKey(modulus, exponent))
                .build().parseClaimsJws(token)
        }
    }

    private fun getUnsignedToken(token: String): String {
        val splitToken: List<String> = token.split(".")
        if (splitToken.size != 3) throw InvalidTokenException
        return splitToken[0] + "." + splitToken[1] + "."
    }

    private fun getUnsignedTokenClaims(token: String, iss: String, aud: String): Jwt<Header<*>, Claims> {
        return JwtParseExecuter {
            Jwts.parserBuilder()
                .requireAudience(aud)
                .requireIssuer(iss)
                .build()
                .parseClaimsJwt(getUnsignedToken(token))
        }
    }

    private fun getRSAPublicKey(modulus: String, exponent: String): Key {
        val keyFactory = KeyFactory.getInstance("RSA")
        val decodeN: ByteArray = Base64.getUrlDecoder().decode(modulus)
        val decodeE: ByteArray = Base64.getUrlDecoder().decode(exponent)
        val n = BigInteger(1, decodeN)
        val e = BigInteger(1, decodeE)

        val keySpec = RSAPublicKeySpec(n, e)
        return keyFactory.generatePublic(keySpec)
    }
}