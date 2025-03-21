package com.photo.server.starsnap.domain.auth.service

import com.photo.server.starsnap.domain.auth.dto.Oauth2LoginDto
import com.photo.server.starsnap.domain.auth.dto.Oauth2SignupDto
import com.photo.server.starsnap.domain.auth.dto.TokenDto
import com.photo.server.starsnap.domain.auth.entity.RefreshTokenEntity
import com.photo.server.starsnap.domain.auth.error.exception.ExistEmailException
import com.photo.server.starsnap.domain.auth.error.exception.NotFoundOauthAccountException
import com.photo.server.starsnap.domain.auth.error.exception.NotFoundUserException
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
import com.photo.server.starsnap.global.security.jwt.JwtProvider
import com.photo.server.starsnap.global.service.AwsS3Service
import org.springframework.stereotype.Service

@Service
class Oauth2Service(
    private val oauth2Repository: Oauth2Repository,
    private val userRepository: UserRepository,
    private val googleOauthHelper: GoogleOauthHelper,
    private val appleOauthHelper: AppleOauthHelper,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtProvider: JwtProvider,
    private val awsS3Service: AwsS3Service
) {
    fun login(loginDto: Oauth2LoginDto): TokenDto {
        val oidcDecodePayload = when (loginDto.type.toOauth2()) {
            Oauth2.GOOGLE -> googleOauthHelper.getOIDCDecodePayload(loginDto.token)
            Oauth2.APPLE -> appleOauthHelper.getOIDCDecodePayload(loginDto.token)
        }

        val oauth2 = oauth2Repository.findByTypeAndEmail(oidcDecodePayload.type, oidcDecodePayload.email)

        val user = userRepository.findByOauth2(oauth2) ?: throw NotFoundUserException
        val tokenDto = jwtProvider.receiveToken(user.id, user.authority)
        val refreshTokenEntity = RefreshTokenEntity(user.id, tokenDto.refreshToken)
        refreshTokenRepository.save(refreshTokenEntity)
        return tokenDto
    }

    fun signup(signupDto: Oauth2SignupDto) {
        val oidcDecodePayload = when (signupDto.type.toOauth2()) {
            Oauth2.GOOGLE -> googleOauthHelper.getOIDCDecodePayload(signupDto.token)
            Oauth2.APPLE -> appleOauthHelper.getOIDCDecodePayload(signupDto.token)
        }
        if (userRepository.existsByEmail(oidcDecodePayload.email)) throw ExistEmailException

        val user = UserEntity(
            username = signupDto.username,
            email = oidcDecodePayload.email,
            authority = Authority.USER,
            state = true
        )

        userRepository.save(user)

        val oauth2 = Oauth2Entity(
            type = oidcDecodePayload.type,
            email = oidcDecodePayload.email,
            sub = oidcDecodePayload.sub,
            userId = user
        )
        oauth2Repository.save(oauth2)
        awsS3Service.uploadUrlToS3(oidcDecodePayload.profileImageUrl, "profile/${user.id}", user.id)
    }

    fun unconnected(idToken: String, user: UserEntity, type: String) {
        val oidcDecodePayload = when (type.toOauth2()) {
            Oauth2.GOOGLE -> googleOauthHelper.getOIDCDecodePayload(idToken)
            Oauth2.APPLE -> appleOauthHelper.getOIDCDecodePayload(idToken)
        }
        val oauth2 = oauth2Repository.findByTypeAndEmail(oidcDecodePayload.type, oidcDecodePayload.email)
            ?: throw NotFoundOauthAccountException

        oauth2Repository.delete(oauth2)
    }

    fun connection(idToken: String, user: UserEntity, type: String) {
        val oidcDecodePayload = when (type.toOauth2()) {
            Oauth2.GOOGLE -> googleOauthHelper.getOIDCDecodePayload(idToken)
            Oauth2.APPLE -> appleOauthHelper.getOIDCDecodePayload(idToken)
        }

        if (oauth2Repository.existsByTypeAndEmail(
                oidcDecodePayload.type,
                oidcDecodePayload.email
            )
        ) throw NotFoundOauthAccountException

        val oauth2 = Oauth2Entity(
            type = oidcDecodePayload.type,
            email = oidcDecodePayload.email,
            sub = oidcDecodePayload.sub,
            userId = user
        )

        oauth2Repository.save(oauth2)
    }
}