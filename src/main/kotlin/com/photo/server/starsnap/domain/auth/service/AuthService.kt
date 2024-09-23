package com.photo.server.starsnap.domain.auth.service

import com.photo.server.starsnap.domain.auth.entity.RefreshTokenEntity
import com.photo.server.starsnap.domain.auth.controller.dto.SignupDto
import com.photo.server.starsnap.domain.auth.controller.dto.TokenDto
import com.photo.server.starsnap.domain.auth.error.exception.ExistEmailException
import com.photo.server.starsnap.global.error.exception.ExistUsernameException
import com.photo.server.starsnap.domain.auth.error.exception.InvalidPasswordException
import com.photo.server.starsnap.domain.auth.repository.RefreshTokenRepository
import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.user.controller.dto.ChangePasswordDto
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.error.exception.NotExistUserIdException
import com.photo.server.starsnap.global.security.jwt.JwtProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import io.viascom.nanoid.NanoId
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull

@Service
class AuthService(
    private val jwtProvider: JwtProvider,
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun login(username: String, password: String): TokenDto {

        val userData = userRepository.findByUsername(username) ?: throw NotExistUserIdException

        matchesPassword(password, userData.password)

        val tokenDto = jwtProvider.receiveToken(userData.id, userData.authority)
        val refreshTokenEntity = RefreshTokenEntity(
            token = tokenDto.refreshToken, id = userData.id
        )

        refreshTokenRepository.save(refreshTokenEntity)

        return tokenDto
    }

    fun signup(signupDto: SignupDto) {

        checkValidEmail(signupDto.email)
        checkValidUsername(signupDto.username)
        val userId = NanoId.generate(16)

        val userData = UserEntity(
            id = userId,
            username = signupDto.username,
            password = signupDto.password,
            email = signupDto.email,
            authority = Authority.USER,
            follow = 0,
            follower = 0
        )

        // password hash
        userData.hashPassword(passwordEncoder)

        userRepository.save(userData)
    }

    fun deleteUser(userId: String) {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotExistUserIdException

        val refreshToken = refreshTokenRepository.findByIdOrNull(user.id)
        if (refreshToken != null) refreshTokenRepository.delete(refreshToken)

        userRepository.delete(user)

    }

    fun changePassword(changePasswordDto: ChangePasswordDto): StatusDto {
        val userData = userRepository.findByUsername(changePasswordDto.userId) ?: throw NotExistUserIdException
        matchesPassword(changePasswordDto.password, userData.password)

        userData.password = changePasswordDto.newPassword
        userData.hashPassword(passwordEncoder)

        userRepository.save(userData)

        return StatusDto("OK", 200)
    }

    fun checkValidUsername(username: String) {
        if (userRepository.existsByUsername(username)) throw ExistUsernameException
    }

    fun checkValidEmail(email: String) {
        if (userRepository.existsByEmail(email)) throw ExistEmailException
    }

    private fun matchesPassword(password: String, sparePassword: String) {
        if (!passwordEncoder.matches(password, sparePassword)) throw InvalidPasswordException
    }

}