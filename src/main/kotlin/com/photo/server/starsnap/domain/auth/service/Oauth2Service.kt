package com.photo.server.starsnap.domain.auth.service

import com.photo.server.starsnap.domain.auth.dto.TokenDto
import com.photo.server.starsnap.domain.auth.entity.RefreshTokenEntity
import com.photo.server.starsnap.domain.auth.repository.Oauth2Repository
import com.photo.server.starsnap.domain.auth.repository.RefreshTokenRepository
import com.photo.server.starsnap.domain.auth.service.helper.AppleOauthHelper
import com.photo.server.starsnap.domain.auth.service.helper.GoogleOauthHelper
import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.auth.type.Oauth2
import com.photo.server.starsnap.domain.auth.type.toOauth2
import com.photo.server.starsnap.domain.user.entity.Oauth2Entity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.domain.user.service.UserAwsS3Service
import com.photo.server.starsnap.global.security.jwt.JwtProvider
import org.springframework.stereotype.Service

@Service
class Oauth2Service(
    private val oauth2Repository: Oauth2Repository,
    private val userRepository: UserRepository,
    private val googleOauthHelper: GoogleOauthHelper,
    private val appleOauthHelper: AppleOauthHelper,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtProvider: JwtProvider,
    private val userAwsS3Service: UserAwsS3Service
) {
    fun login(token: String, type: String): TokenDto {
        val oidcDecodePayload = when (type.toOauth2()) {
            Oauth2.GOOGLE -> googleOauthHelper.getOIDCDecodePayload(token)
            Oauth2.APPLE -> appleOauthHelper.getOIDCDecodePayload(token)
        }
        val oauth2 = oauth2Repository.findByTypeAndEmail(oidcDecodePayload.type, oidcDecodePayload.email)

        val user = userRepository.findByOauth2(oauth2) ?: throw RuntimeException("없는 사람임")
        val tokenDto = jwtProvider.receiveToken(user.id, user.authority)
        val refreshTokenEntity = RefreshTokenEntity(
            token = tokenDto.refreshToken, id = user.id
        )
        refreshTokenRepository.save(refreshTokenEntity)
        return tokenDto
    }

    fun signup(token: String, type: String, username: String) {
        val oidcDecodePayload = when (type.toOauth2()) {
            Oauth2.GOOGLE -> googleOauthHelper.getOIDCDecodePayload(token)
            Oauth2.APPLE -> appleOauthHelper.getOIDCDecodePayload(token)
        }
        if (userRepository.existsByEmail(oidcDecodePayload.email)) throw RuntimeException("이미 사용중인 email")

        val user = UserEntity(
            username = username,
            nickname = oidcDecodePayload.nickname,
            email = oidcDecodePayload.email,
            authority = Authority.USER,
            password = null,
            profileImageUrl = "",
            followerCount = 0,
            followingCount = 0
        )

        userRepository.save(user)

        val oauth2 = Oauth2Entity(
            type = oidcDecodePayload.type,
            email = oidcDecodePayload.email,
            sub = oidcDecodePayload.sub,
            userId = user
        )
        oauth2Repository.save(oauth2)
        userAwsS3Service.addOauthProfileImage(user.id, oidcDecodePayload.profileImageUrl)
    }

    fun unconnected(idToken: String, user: UserEntity, type: String) {
        val oidcDecodePayload = when (type.toOauth2()) {
            Oauth2.GOOGLE -> googleOauthHelper.getOIDCDecodePayload(idToken)
            Oauth2.APPLE -> appleOauthHelper.getOIDCDecodePayload(idToken)
        }
        val oauth2 = oauth2Repository.findByTypeAndEmail(oidcDecodePayload.type, oidcDecodePayload.email) ?: throw RuntimeException("존재하지 않는 계정")

        oauth2Repository.delete(oauth2)
    }

    fun connection(idToken: String, user: UserEntity, type: String) {
        val oidcDecodePayload = when (type.toOauth2()) {
            Oauth2.GOOGLE -> googleOauthHelper.getOIDCDecodePayload(idToken)
            Oauth2.APPLE -> appleOauthHelper.getOIDCDecodePayload(idToken)
        }

        if(oauth2Repository.existsByTypeAndEmail(oidcDecodePayload.type, oidcDecodePayload.email)) throw RuntimeException("존재하는 계정")

        val oauth2 = Oauth2Entity(
            type = oidcDecodePayload.type,
            email = oidcDecodePayload.email,
            sub = oidcDecodePayload.sub,
            userId = user
        )

        oauth2Repository.save(oauth2)
    }
}