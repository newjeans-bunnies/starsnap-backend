package com.photo.server.starsnap.domain.auth.service

import com.photo.server.starsnap.domain.auth.dto.LoginDto
import com.photo.server.starsnap.domain.auth.entity.RefreshTokenEntity
import com.photo.server.starsnap.domain.auth.dto.SignupDto
import com.photo.server.starsnap.domain.auth.dto.TokenDto
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
import org.springframework.data.repository.findByIdOrNull

@Service
class AuthService(
    private val jwtProvider: JwtProvider,
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    fun login(username: String, password: String): TokenDto {

        val userData = userRepository.findByUsername(username) ?: throw NotExistUserIdException

        matchesPassword(password, userData.password ?: throw RuntimeException("존재 하지 않는 유저"))

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
            username = signupDto.username,
            password = signupDto.password,
            email = signupDto.email,
            authority = Authority.USER,
            state = true
        )

        // password hash
        userData.hashPassword(passwordEncoder)

        userRepository.save(userData)
    }

    fun deleteUser(userId: String) {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotExistUserIdException
        user.state = false

        userRepository.save(user)

        val refreshToken = refreshTokenRepository.findByIdOrNull(user.id)
        if (refreshToken != null) refreshTokenRepository.delete(refreshToken)
    }

    fun changePassword(changePasswordDto: ChangePasswordDto): StatusDto {
        val userData = userRepository.findByUsername(changePasswordDto.username) ?: throw NotExistUserIdException
        matchesPassword(changePasswordDto.password, userData.password ?: throw RuntimeException("권한 없음"))

        userData.password = changePasswordDto.newPassword
        userData.hashPassword(passwordEncoder)

        userRepository.save(userData)

        return StatusDto("OK", 200)
    }

    fun setPassword(password: String, userId: String) {
        val userData = userRepository.findByIdOrNull(userId) ?: throw NotExistUserIdException
        if (userData.password != null) throw RuntimeException("비밀번호가 설정 되어 있음")
        userData.password = password
        userData.hashPassword(passwordEncoder)
        userRepository.save(userData)
    }

    fun userRollback(loginDto: LoginDto): TokenDto {
        val userData = userRepository.findByUsername(loginDto.username) ?: throw NotExistUserIdException
        if(!userData.state) throw RuntimeException("사용 가능한 계정입니다.")
        matchesPassword(loginDto.password, userData.password ?: throw RuntimeException("존재 하지 않는 유저"))
        val tokenDto = jwtProvider.receiveToken(userData.id, userData.authority)

        val refreshTokenEntity = RefreshTokenEntity(
            token = tokenDto.refreshToken, id = userData.id
        )

        refreshTokenRepository.save(refreshTokenEntity)

        return tokenDto
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