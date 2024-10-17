package com.photo.server.starsnap.global.service

import com.photo.server.starsnap.domain.auth.repository.Oauth2Repository
import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.user.entity.Oauth2Entity
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.global.dto.OAuthDto
import com.photo.server.starsnap.global.dto.of
import com.photo.server.starsnap.global.security.principle.CustomUserDetails
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.stereotype.Service
import io.viascom.nanoid.NanoId
import jakarta.transaction.Transactional
import org.springframework.security.oauth2.core.OAuth2Error

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
    private val oauth2Repository: Oauth2Repository
) : DefaultOAuth2UserService() {

    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        val oAuth2UserAttributes = oAuth2User.attributes
        // google
        val registrationId = userRequest.clientRegistration.registrationId
        val oAuthDto = userRequest.of(registrationId, oAuth2UserAttributes)
        val user = getOrSave(oAuthDto)
        return CustomUserDetails(user.id, user, user.authority, oAuth2User)
    }

    @Transactional
    fun getOrSave(
        oauthDto: OAuthDto
    ): UserEntity {
        val error = OAuth2Error("REDIRECT_TO_SIGNUP", "REDIRECT_TO_SIGNUP", null)

        val oauth2 = oauth2Repository.findByTypeAndEmail(oauthDto.type, oauthDto.email) ?: throw OAuth2AuthenticationException(error)
        val existingUser = userRepository.findByOauth2(oauth2)

        return existingUser ?: run {
            val user = UserEntity(
                authority = Authority.USER,
                email = oauthDto.email,
                username = NanoId.generate(12, "abcdefghijklmnopqrstuvwxyz"),
                nickname = oauthDto.name,
                profileImageUrl = oauthDto.profileImage,
                password = null,
                followerCount = 0,
                followingCount = 0
            )

            val oauth2 = Oauth2Entity(
                type = oauthDto.type,
                email = oauthDto.email,
                userId = user,
                sub = oauthDto.sub
            )

            oauth2Repository.save(oauth2)
            userRepository.save(user)
        }
    }
}