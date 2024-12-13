package com.photo.server.starsnap.global.filter

import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.auth.type.Token
import com.photo.server.starsnap.global.error.exception.InvalidRoleException
import com.photo.server.starsnap.global.security.jwt.JwtParser
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AdminFilter(
    private val jwtParser: JwtParser
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestURI = request.requestURI
        if (requestURI.startsWith("/api/star-group") || requestURI.startsWith("/api/star") || requestURI.startsWith("/api/fandom/set")) {
            val token = resolveToken(request) ?: throw InvalidRoleException
            authorizeToken(token)
        }
        filterChain.doFilter(request, response)
    }

    private fun authorizeToken(token: String) {
        val tokenClaims = jwtParser.getClaims(token, Token.ACCESS)
        val authority = tokenClaims.body["authority"] as String
        if (authority != Authority.ADMIN.name) throw InvalidRoleException
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken?.startsWith("Bearer ") == true) {
            bearerToken.substring(7)
        } else null
    }

}