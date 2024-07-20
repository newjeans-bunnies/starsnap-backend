package com.photo.server.starsnap.domain.auth.service

import com.photo.server.starsnap.domain.auth.RefreshTokenEntity
import com.photo.server.starsnap.domain.auth.controller.dto.SignupDto
import com.photo.server.starsnap.domain.auth.controller.dto.TokenDto
import com.photo.server.starsnap.domain.auth.error.exception.ExistEmailException
import com.photo.server.starsnap.global.error.exception.ExistUsernameException
import com.photo.server.starsnap.domain.auth.error.exception.InvalidPasswordException
import com.photo.server.starsnap.domain.auth.repository.RefreshTokenRepository
import com.photo.server.starsnap.domain.auth.type.Authority
import com.photo.server.starsnap.domain.user.UserEntity
import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.error.exception.NotExistUserIdException
import com.photo.server.starsnap.global.security.jwt.JwtProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import io.viascom.nanoid.NanoId
import org.springframework.data.repository.findByIdOrNull

@Service
class AuthService(
    private val jwtProvider: JwtProvider,
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val refreshTokenRepository: RefreshTokenRepository
) {

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

        val userData = UserEntity(
            id = NanoId.generate(12),
            username = signupDto.username,
            password = signupDto.password,
            email = signupDto.email,
            authority = Authority.USER
        )

        // password hash
        userData.hashPassword(passwordEncoder)

        userRepository.save(userData)
    }

    fun deleteUser(userId: String): StatusDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotExistUserIdException

        val refreshToken = refreshTokenRepository.findByIdOrNull(user.id)
        if (refreshToken != null) refreshTokenRepository.delete(refreshToken)

        userRepository.delete(user)

        return StatusDto("Deleted", 204)
    }

    fun fixPassword() {

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