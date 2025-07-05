package com.photo.server.starsnap.adapter_infrastructure.global.security.jwt

import com.photo.server.starsnap.exception.global.error.exception.ExpiredTokenException
import com.photo.server.starsnap.exception.global.error.exception.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException

fun jwtParseExecuter(operation: () -> Jwt<Header<*>, Claims>): Jwt<Header<*>, Claims> {
    return try {
        operation()
    } catch (ex: Exception) {
        when (ex) {
            is io.jsonwebtoken.security.SecurityException,
            is MalformedJwtException,
            is UnsupportedJwtException,
            is IllegalArgumentException,
                -> throw InvalidTokenException
            is ExpiredJwtException -> throw ExpiredTokenException
            else -> throw ex
        }
    }
}

fun jwsParseExecuter(operation: () -> Jws<Claims>): Jws<Claims> {
    return try {
        operation()
    } catch (ex: Exception) {
        when (ex) {
            is io.jsonwebtoken.security.SecurityException,
            is MalformedJwtException,
            is UnsupportedJwtException,
            is IllegalArgumentException,
                -> throw InvalidTokenException
            is ExpiredJwtException -> throw ExpiredTokenException
            else -> throw ex
        }
    }
}